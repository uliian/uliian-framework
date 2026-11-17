package com.uliian.framework.components.convert

import com.baomidou.mybatisplus.annotation.EnumValue
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.lang.reflect.Field
import java.util.concurrent.ConcurrentHashMap


/**
 * Created with IntelliJ IDEA.
 * @author Anders Xiao
 * @date 2019-10-15
 */
object EnumSerializer : JsonSerializer<Enum<*>>() {
    private val enumValueFieldCache = ConcurrentHashMap<Class<*>, Field?>()

    override fun serialize(value: Enum<*>?, gen: JsonGenerator, serializers: SerializerProvider?) {
        val describer = value as? IDescribeEnum<*>

        when {
            value == null -> gen.writeNull()
            describer != null -> gen.writeNumber(describer.code.toInt())
            else -> serializeEnumValueField(value, gen)
        }
    }

    private fun serializeEnumValueField(value: Enum<*>, gen: JsonGenerator) {
        val enumValueField = enumValueFieldCache.computeIfAbsent(value::class.java) { clazz ->
            clazz.declaredFields.firstOrNull { it.isAnnotationPresent(EnumValue::class.java) }
                ?.apply { isAccessible = true }
        }

        if (enumValueField != null) {
            val data = enumValueField.get(value)
            gen.writeObject(data)
        } else {
            gen.writeNumber(value.ordinal)
        }
    }
}
