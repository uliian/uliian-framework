package com.uliian.framework.web.convert

import com.baomidou.mybatisplus.annotation.EnumValue
import com.uliian.framework.components.convert.IDescribeEnum
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private val factory = EnhanceStringToEnumConverterFactory()

enum class NumberCode(override val code: Int, override val description: String) : IDescribeEnum<Int> {
    ONE(1, "one"), TWO(2, "two")
}

enum class AnnotatedInt(@field:EnumValue val code: Int) { FOO(7), BAR(8) }

enum class AnnotatedString(@field:EnumValue val code: String) { ALPHA("a1"), BETA("b2") }

enum class PlainEnum { FIRST, SECOND }

class EnhanceStringToEnumConverterFactoryTest {

    @Test
    fun `matches @EnumValue annotated int`() {
        val converter = factory.getConverter(AnnotatedInt::class.java)
        assertEquals(AnnotatedInt.BAR, converter.convert("8"))
    }

    @Test
    fun `matches @EnumValue annotated string`() {
        val converter = factory.getConverter(AnnotatedString::class.java)
        assertEquals(AnnotatedString.ALPHA, converter.convert("a1"))
    }

    @Test
    fun `matches IDescribeEnum code`() {
        val converter = factory.getConverter(NumberCode::class.java)
        assertEquals(NumberCode.TWO, converter.convert("2"))
    }

    @Test
    fun `matches enum name case-insensitively`() {
        val converter = factory.getConverter(PlainEnum::class.java)
        assertEquals(PlainEnum.SECOND, converter.convert("second"))
    }

    @Test
    fun `falls back to ordinal`() {
        val converter = factory.getConverter(PlainEnum::class.java)
        assertEquals(PlainEnum.FIRST, converter.convert("0"))
    }

    @Test
    fun `returns null for blank input`() {
        val converter = factory.getConverter(PlainEnum::class.java)
        assertEquals(null, converter.convert("   "))
    }
}
