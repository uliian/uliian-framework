package com.uliian.framework.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.uliian.framework.components.annotation.EnumProperty
import com.uliian.framework.web.convert.EnumDeserializer
import com.uliian.framework.web.convert.EnumSerializer
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.assertTrue

enum class Test(@EnumProperty val code: Int) {
    E1(1),
    E2(2)
}

data class TestObj(val test: Test,val name:String)

class TestObj2{
    var test:Test = Test.E1
    var name:String = ""
}
class EnumPropertyTest {
    @org.junit.jupiter.api.Test
    fun test1() {
        val objectMapper = ObjectMapper()
        val module = SimpleModule().addSerializer(Enum::class.java, EnumSerializer)
        objectMapper.registerModule(module)

        val value = Test.E1
        val expectedJson = "1"
        val actualJson = objectMapper.writeValueAsString(value)

        assertEquals(expectedJson, actualJson)
    }

    @org.junit.jupiter.api.Test
    fun complexTest() {
        val obj = TestObj(Test.E1, "test")
        val objectMapper = ObjectMapper()
        val module = SimpleModule().addSerializer(Enum::class.java, EnumSerializer)
        objectMapper.registerModule(module)

        val actualJson = objectMapper.writeValueAsString(obj)
        assertTrue { actualJson.contains("\"test\":1") }
    }

    @org.junit.jupiter.api.Test
    fun decodeTest(){
        val objectMapper = ObjectMapper()
        val module = SimpleModule().addSerializer(Enum::class.java, EnumSerializer)
        module.addDeserializer(Enum::class.java, EnumDeserializer())

        objectMapper.registerModule(module)
        val json = "{\"test\":1,\"name\":\"test\"}"
        val readValue = objectMapper.readValue<TestObj2>(json, TestObj2::class.java)
        assertEquals(readValue.test,Test.E1)

        val json2 = "{\"test\":2,\"name\":\"test\"}"
        val readValue2 = objectMapper.readValue<TestObj2>(json2, TestObj2::class.java)
        assertEquals(readValue2.test,Test.E2)
    }
}