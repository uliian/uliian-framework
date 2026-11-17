package com.uliian.framework.components.convert

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
        if (value == null) {
            gen.writeNull()
            return
        }
        gen.writeObject(EnumCodec.serializedValue(value))
    }
}
