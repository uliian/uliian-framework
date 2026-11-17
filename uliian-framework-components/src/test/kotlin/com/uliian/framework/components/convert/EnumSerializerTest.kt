package com.uliian.framework.components.convert

import com.baomidou.mybatisplus.annotation.EnumValue
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.databind.module.SimpleModule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

private fun mapperWithEnumCodec(): ObjectMapper {
    val module = SimpleModule()
        .addSerializer(Enum::class.java, EnumSerializer)
        .addDeserializer(Enum::class.java, EnumDeserializer())
    return ObjectMapper()
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

enum class LongCodeEnum(override val code: Long, override val description: String) : IDescribeEnum<Long> {
    LARGE(4_294_967_296L, "large")
}

enum class AnnotatedDescribeEnum(
    @EnumValue private val wireValue: String,
    override val code: Int,
    override val description: String
) : IDescribeEnum<Int> {
    ACTIVE("active", 100, "active")
}

enum class PlainEnum { ALPHA, BETA }

data class Wrapper(val value: PlainEnum?)

class EnumSerializerTest {

    @Test
    fun `serializes IDescribeEnum using its code`() {
        val json = mapperWithEnumCodec().writeValueAsString(DescribeEnum.ONE)
        assertEquals("10", json)
    }

    @Test
    fun `serializes @EnumValue field even when private`() {
        val json = mapperWithEnumCodec().writeValueAsString(AnnotatedEnum.BAR)
        assertEquals("8", json)
    }

    @Test
    fun `serializes @EnumValue field with non numeric type`() {
        val json = mapperWithEnumCodec().writeValueAsString(StringCodeEnum.FIRST)
        assertEquals("\"alpha\"", json)
    }

    @Test
    fun `falls back to ordinal when no hints`() {
        val json = mapperWithEnumCodec().writeValueAsString(PlainEnum.ALPHA)
        assertEquals("0", json)
    }

    @Test
    fun `writes null enum property as null`() {
        val json = mapperWithEnumCodec().writeValueAsString(Wrapper(null))
        assertEquals("{\"value\":null}", json)
    }

    @Test
    fun `round trips private numeric @EnumValue`() {
        val mapper = mapperWithEnumCodec()
        val json = mapper.writeValueAsString(AnnotatedEnum.BAR)
        assertEquals(AnnotatedEnum.BAR, mapper.readValue(json, AnnotatedEnum::class.java))
    }

    @Test
    fun `round trips string @EnumValue`() {
        val mapper = mapperWithEnumCodec()
        val json = mapper.writeValueAsString(StringCodeEnum.SECOND)
        assertEquals(StringCodeEnum.SECOND, mapper.readValue(json, StringCodeEnum::class.java))
    }

    @Test
    fun `preserves long IDescribeEnum code`() {
        val mapper = mapperWithEnumCodec()
        val json = mapper.writeValueAsString(LongCodeEnum.LARGE)
        assertEquals("4294967296", json)
        assertEquals(LongCodeEnum.LARGE, mapper.readValue(json, LongCodeEnum::class.java))
    }

    @Test
    fun `prefers @EnumValue and accepts IDescribeEnum code as alias`() {
        val mapper = mapperWithEnumCodec()
        assertEquals("\"active\"", mapper.writeValueAsString(AnnotatedDescribeEnum.ACTIVE))
        assertEquals(AnnotatedDescribeEnum.ACTIVE, mapper.readValue("100", AnnotatedDescribeEnum::class.java))
    }

    @Test
    fun `matches enum name case insensitively`() {
        assertEquals(PlainEnum.BETA, mapperWithEnumCodec().readValue("\"beta\"", PlainEnum::class.java))
    }

    @Test
    fun `deserializes enum collection elements contextually`() {
        val mapper = mapperWithEnumCodec()
        val listType = mapper.typeFactory.constructCollectionType(List::class.java, StringCodeEnum::class.java)
        val values: List<StringCodeEnum> = mapper.readValue("[\"alpha\",\"beta\"]", listType)
        assertEquals(listOf(StringCodeEnum.FIRST, StringCodeEnum.SECOND), values)
    }

    @Test
    fun `reports accepted values for invalid input`() {
        val exception = assertThrows(InvalidFormatException::class.java) {
            mapperWithEnumCodec().readValue("\"unknown\"", StringCodeEnum::class.java)
        }
        assertTrue(exception.message!!.contains("accepted values: [alpha, beta, first, second, 0, 1]"))
    }
}
