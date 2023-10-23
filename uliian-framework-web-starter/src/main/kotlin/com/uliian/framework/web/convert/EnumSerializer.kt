package com.uliian.framework.web.convert

import com.baomidou.mybatisplus.annotation.EnumValue
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider


/**
 * Created with IntelliJ IDEA.
 * @author Anders Xiao
 * @date 2019-10-15
 */
object EnumSerializer : JsonSerializer<Enum<*>>() {
    override fun serialize(value: Enum<*>?, gen: JsonGenerator, serializers: SerializerProvider?) {
        val describer = value as? IDescribeEnum<*>

        if (value == null) {
            gen.writeNull()
        } else if (describer != null) {
            gen.writeNumber(describer.code.toInt())
        } else {
            val clazz = value::class.java

            val annotationField = clazz.declaredFields.firstOrNull { it.isAnnotationPresent(EnumValue::class.java) }
            if (annotationField != null) {
                val filedName = annotationField.name
                val getMethodName = filedName[0].toUpperCase() + filedName.substring(1)
                val getMethod = clazz.getMethod("get${getMethodName}")
                val data = getMethod.invoke(value)
                gen.writeNumber(data as Int)
            } else {
                gen.writeNumber(value.ordinal)
            }
        }
    }
}