package com.uliian.framework.web.convert

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeDeserializerExt : JsonDeserializer<LocalDateTime>() {
    companion object{
        val ISODes =  LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME)
        val normalDes = LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): LocalDateTime {
        return if(p.text.contains("T")) ISODes.deserialize(p,ctxt) else normalDes.deserialize(p,ctxt)
    }
}