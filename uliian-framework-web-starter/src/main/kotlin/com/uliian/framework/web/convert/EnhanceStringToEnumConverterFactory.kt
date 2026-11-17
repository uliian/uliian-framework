package com.uliian.framework.web.convert

import com.uliian.framework.components.convert.EnumCodec
import org.springframework.core.convert.converter.Converter
import org.springframework.core.convert.converter.ConverterFactory

class EnhanceStringToEnumConverterFactory: ConverterFactory<String, Enum<*>> {
    override fun <T : Enum<*>> getConverter(targetType: Class<T>): Converter<String, T> {
        @Suppress("UNCHECKED_CAST")
        return StringToEnumConverter(targetType as Class<out Enum<*>>) as Converter<String, T>
    }

    private class StringToEnumConverter(
        private val enumType: Class<out Enum<*>>
    ) : Converter<String, Enum<*>?> {
        override fun convert(source: String): Enum<*>? {
            val text = source.trim()
            if (text.isEmpty()) {
                return null
            }
            return EnumCodec.resolve(enumType, text)
                ?: throw IllegalArgumentException(
                    "Value '$source' is not valid for ${enumType.name}; accepted values: ${EnumCodec.acceptedValues(enumType)}"
                )
        }
    }
}
