package com.uliian.framework.core.convert

import com.baomidou.mybatisplus.annotation.EnumValue
import org.springframework.core.convert.converter.Converter
import org.springframework.core.convert.converter.ConverterFactory
import org.springframework.util.Assert

class EnhanceStringToEnumConverterFactory: ConverterFactory<String, Enum<*>> {
    override fun <T : Enum<*>> getConverter(targetType: Class<T>): Converter<String, T> {
        val enumType = getEnumType(targetType) as Class<T>
        return StringToEnumConverter(enumType)
    }

    private fun getEnumType(targetType: Class<*>): Class<*> {
        var enumType: Class<*> = targetType
        while (!enumType.isEnum) {
            enumType = enumType.superclass
        }
        Assert.notNull(enumType) { "The target type " + targetType.name + " does not refer to an enum" }
        return enumType
    }

    private inner class StringToEnumConverter<E : Enum<*>>(private val enumType: Class<E>) : Converter<String, E> {

        fun getEnumValue(enumClassName: String, enumValue: String): Any {
            val enumClz = Class.forName(enumClassName).enumConstants.map {
                it as Enum<*>
            }


            return enumClz.first {
                val enum = it as Enum<*>
                val field = it.javaClass.declaredFields.firstOrNull { it.getDeclaredAnnotation(EnumValue::class.java)!=null }
                if (field != null) {
                    val filedName = field.name
                    val getMethodName = filedName[0].toUpperCase() + filedName.substring(1)
                    val getMethod = enumType.getMethod("get${getMethodName}")
                    return enumClz.firstOrNull{
                        getMethod.invoke(it).toString() == enumValue.toString()
                    } as Any
                }
                if(enum.name.equals(enumValue, ignoreCase = true)){
                    true
                }else {
                    val desc = it as? IDescribeEnum<*>
                    val ordinal = desc?.code?.toString() ?: it.ordinal.toString()
                    ordinal == enumValue
                }
            }
        }

        override fun convert(source: String): E? {
            return if (source.isEmpty()) {
                null
            } else  getEnumValue(enumType.name, source) as? E
        }
    }

}