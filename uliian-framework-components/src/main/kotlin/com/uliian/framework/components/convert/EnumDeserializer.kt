package com.uliian.framework.components.convert

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.deser.ContextualDeserializer

/**
 * Created with IntelliJ IDEA.
 * @author Anders Xiao
 * @date 2019-10-15
 */
class EnumDeserializer(
    private val enumClass: Class<out Enum<*>>? = null
) : JsonDeserializer<Enum<*>>(), ContextualDeserializer {

    override fun createContextual(ctxt: DeserializationContext, property: BeanProperty?): JsonDeserializer<*> {
        val rawClass = sequenceOf(ctxt.contextualType, property?.type)
            .filterNotNull()
            .map { it.rawClass }
            .firstOrNull { it.isEnum }
            ?: throw JsonMappingException.from(
                ctxt.parser,
                "Cannot determine enum type for ${property?.name ?: "root value"}"
            )

        @Suppress("UNCHECKED_CAST")
        return EnumDeserializer(rawClass as Class<out Enum<*>>)
    }

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Enum<*>? {
        val targetClass = enumClass ?: contextualEnumClass(ctxt)
        val rawValue: Any = when (p.currentToken()) {
            JsonToken.VALUE_STRING -> p.text.trim().also { if (it.isEmpty()) return null }
            JsonToken.VALUE_NUMBER_INT, JsonToken.VALUE_NUMBER_FLOAT -> p.numberValue
            else -> return ctxt.handleUnexpectedToken(targetClass, p) as Enum<*>?
        }

        return EnumCodec.resolve(targetClass, rawValue) ?: when (rawValue) {
            is Number -> throw ctxt.weirdNumberException(rawValue, targetClass, invalidValueMessage(targetClass, rawValue))
            else -> throw ctxt.weirdStringException(rawValue.toString(), targetClass, invalidValueMessage(targetClass, rawValue))
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun contextualEnumClass(ctxt: DeserializationContext): Class<out Enum<*>> {
        val rawClass = ctxt.contextualType?.rawClass
        require(rawClass?.isEnum == true) { "Cannot determine enum type from Jackson context" }
        return rawClass as Class<out Enum<*>>
    }

    private fun invalidValueMessage(enumClass: Class<out Enum<*>>, rawValue: Any): String =
        "Value '$rawValue' is not valid for ${enumClass.name}; accepted values: ${EnumCodec.acceptedValues(enumClass)}"
}
