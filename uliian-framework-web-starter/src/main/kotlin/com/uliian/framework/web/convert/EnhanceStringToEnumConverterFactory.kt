package com.uliian.framework.web.convert

import com.baomidou.mybatisplus.annotation.EnumValue
import com.uliian.framework.components.convert.IDescribeEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.core.convert.converter.ConverterFactory
import org.springframework.util.Assert
import java.lang.reflect.Field
import java.util.concurrent.ConcurrentHashMap

class EnhanceStringToEnumConverterFactory: ConverterFactory<String, Enum<*>> {

    private val enumValueFieldCache = ConcurrentHashMap<Class<*>, Field?>()

    override fun <T : Enum<*>> getConverter(targetType: Class<T>): Converter<String, T> {
        val enumType = getEnumType(targetType) as Class<T>
        @Suppress("UNCHECKED_CAST")
        return StringToEnumConverter(enumType) as Converter<String, T>
    }

    private fun getEnumType(targetType: Class<*>): Class<*> {
        var enumType: Class<*> = targetType
        while (!enumType.isEnum) {
            enumType = enumType.superclass
        }
        Assert.notNull(enumType) { "The target type " + targetType.name + " does not refer to an enum" }
        return enumType
    }

    private inner class StringToEnumConverter<E : Enum<*>>(private val enumType: Class<E>) : Converter<String, E?> {
        private val enumConstants: List<E> = enumType.enumConstants?.map { it as E } ?: emptyList()

        private val enumValueField: Field? = enumValueFieldCache.computeIfAbsent(enumType) { clazz ->
            clazz.declaredFields.firstOrNull { it.isAnnotationPresent(EnumValue::class.java) }
                ?.apply { isAccessible = true }
        }

        private val lookup: Map<String, E> = buildLookup()

        override fun convert(source: String): E? {
            val text = source.trim()
            if (text.isEmpty()) {
                return null
            }
            val key = text.lowercase()
            return lookup[key]
        }

        private fun buildLookup(): Map<String, E> {
            val map = LinkedHashMap<String, E>()
            for (constant in enumConstants) {
                // @EnumValue annotated field
                enumValueField?.get(constant)?.toString()?.lowercase()?.let { map.putIfAbsent(it, constant) }

                // enum name (case-insensitive)
                map.putIfAbsent(constant.name.lowercase(), constant)

                // IDescribeEnum code
                (constant as? IDescribeEnum<*>)?.code?.toString()?.lowercase()?.let { map.putIfAbsent(it, constant) }

                // ordinal fallback
                map.putIfAbsent(constant.ordinal.toString(), constant)
            }
            return map
        }
    }

}
