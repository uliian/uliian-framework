package com.uliian.framework.components.convert

import com.baomidou.mybatisplus.annotation.EnumValue
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
//import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private fun mapperWithEnumSerializer(): ObjectMapper {
    val module = SimpleModule().addSerializer(Enum::class.java, EnumSerializer)
    return ObjectMapper()
//        .registerKotlinModule()
        .registerModule(module)
}

enum class DescribeEnum(override val code: Int, override val description: String) : IDescribeEnum<Int> {
    ONE(10, "one"),
    TWO(20, "two")
}

enum class AnnotatedEnum(@EnumValue private val code: Int) {
    FOO(7), BAR(8)
}

enum class StringCodeEnum(@EnumValue private val code: String) {
    FIRST("alpha"), SECOND("beta")
}

enum class PlainEnum { ALPHA, BETA }

data class Wrapper(val value: PlainEnum?)

class EnumSerializerTest {

    @Test
    fun `serializes IDescribeEnum using its code`() {
        val json = mapperWithEnumSerializer().writeValueAsString(DescribeEnum.ONE)
        assertEquals("10", json)
    }

    @Test
    fun `serializes @EnumValue field even when private`() {
        val json = mapperWithEnumSerializer().writeValueAsString(AnnotatedEnum.BAR)
        assertEquals("8", json)
    }

    @Test
    fun `serializes @EnumValue field with non numeric type`() {
        val json = mapperWithEnumSerializer().writeValueAsString(StringCodeEnum.FIRST)
        assertEquals("\"alpha\"", json)
    }

    @Test
    fun `falls back to ordinal when no hints`() {
        val json = mapperWithEnumSerializer().writeValueAsString(PlainEnum.ALPHA)
        assertEquals("0", json)
    }

    @Test
    fun `writes null enum property as null`() {
        val json = mapperWithEnumSerializer().writeValueAsString(Wrapper(null))
        assertEquals("{\"value\":null}", json)
    }
}
