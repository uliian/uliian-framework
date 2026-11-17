package com.uliian.framework.components.convert

import com.baomidou.mybatisplus.annotation.EnumValue
import com.uliian.framework.components.staticdictionary.StaticDictionaryItem
import java.util.Locale
import java.util.concurrent.ConcurrentHashMap

/**
 * Defines the wire-format rules shared by Jackson and Spring MVC enum conversion.
 */
object EnumCodec {
    private val metadataCache = ConcurrentHashMap<Class<out Enum<*>>, EnumMetadata>()

    fun serializedValue(value: Enum<*>): Any? = metadata(value.javaClass).serializedValue(value)

    fun resolve(enumClass: Class<out Enum<*>>, rawValue: Any?): Enum<*>? {
        if (rawValue == null) {
            return null
        }
        return metadata(enumClass).resolve(rawValue)
    }

    fun acceptedValues(enumClass: Class<out Enum<*>>): List<Any?> = metadata(enumClass).acceptedValues

    private fun metadata(enumClass: Class<out Enum<*>>): EnumMetadata =
        metadataCache.computeIfAbsent(enumClass, ::buildMetadata)

    private fun buildMetadata(enumClass: Class<out Enum<*>>): EnumMetadata {
        val constants = enumClass.enumConstants?.toList()
            ?: throw IllegalArgumentException("${enumClass.name} is not an enum type")
        val enumValueField = enumClass.declaredFields
            .firstOrNull { it.isAnnotationPresent(EnumValue::class.java) }
            ?.also { field ->
                if (!field.trySetAccessible()) {
                    throw IllegalStateException("Cannot access @EnumValue field ${enumClass.name}.${field.name}")
                }
            }

        val serializedValues = constants.associateWith { constant ->
            when {
                constant is StaticDictionaryItem -> constant.value
                enumValueField != null -> enumValueField.get(constant)
                constant is IDescribeEnum<*> -> constant.code
                else -> constant.ordinal
            }
        }

        val lookup = LinkedHashMap<String, Enum<*>>()
        addAliases(lookup, constants) { serializedValues.getValue(it) }
        if (!StaticDictionaryItem::class.java.isAssignableFrom(enumClass)) {
            addAliases(lookup, constants) { (it as? IDescribeEnum<*>)?.code }
            addAliases(lookup, constants) { it.name }
            addAliases(lookup, constants) { it.ordinal }
        }

        return EnumMetadata(
            enumClass = enumClass,
            serializedValues = serializedValues,
            lookup = lookup,
            acceptedValues = lookup.keys.toList()
        )
    }

    private fun addAliases(
        lookup: MutableMap<String, Enum<*>>,
        constants: List<Enum<*>>,
        value: (Enum<*>) -> Any?
    ) {
        constants.forEach { constant ->
            value(constant)?.let { alias -> lookup.putIfAbsent(normalize(alias), constant) }
        }
    }

    private fun normalize(value: Any): String = value.toString().trim().lowercase(Locale.ROOT)

    private data class EnumMetadata(
        val enumClass: Class<out Enum<*>>,
        val serializedValues: Map<Enum<*>, Any?>,
        val lookup: Map<String, Enum<*>>,
        val acceptedValues: List<Any?>
    ) {
        fun serializedValue(value: Enum<*>): Any? {
            require(serializedValues.containsKey(value)) {
                "${value.javaClass.name} is not managed by metadata for ${enumClass.name}"
            }
            return serializedValues[value]
        }

        fun resolve(rawValue: Any): Enum<*>? = lookup[normalize(rawValue)]
    }
}
