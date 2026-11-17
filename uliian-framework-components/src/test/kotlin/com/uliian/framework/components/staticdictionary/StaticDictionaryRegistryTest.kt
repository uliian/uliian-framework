package com.uliian.framework.components.staticdictionary

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.uliian.framework.components.convert.EnumDeserializer
import com.uliian.framework.components.convert.EnumSerializer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

@StaticDictionary("test-status")
private enum class TestStatus(
    override val value: String,
    override val label: String,
    override val desc: String?,
    override val sort: Int?,
    private val color: String
) : StaticDictionaryItem {
    UNSORTED("U", "Unsorted", null, null, "gray"),
    SECOND("S", "Second", "second item", 20, "blue"),
    FIRST("F", "First", "first item", 10, "green");

    override fun toOption(): StaticDictionaryOption = ColoredOption(this, color)
}

private class ColoredOption(item: StaticDictionaryItem, val color: String) :
    StaticDictionaryOption(item.value, item.label, item.desc, item.sort)

class StaticDictionaryRegistryTest {
    private val registry = StaticDictionaryRegistry(listOf(javaClass.packageName))

    @Test
    fun `discovers dictionary and orders null sort last`() {
        val options = registry.get("test-status")

        assertEquals(listOf("F", "S", "U"), options.map { it.value })
        assertInstanceOf(ColoredOption::class.java, options.first())
        assertEquals("green", (options.first() as ColoredOption).color)
    }

    @Test
    fun `rejects unknown dictionary type`() {
        assertThrows(IllegalArgumentException::class.java) { registry.get("missing") }
    }

    @Test
    fun `static dictionary enum uses only value on the wire`() {
        val module = SimpleModule()
            .addSerializer(Enum::class.java, EnumSerializer)
            .addDeserializer(Enum::class.java, EnumDeserializer())
        val mapper = ObjectMapper().registerModule(module)

        assertEquals("\"F\"", mapper.writeValueAsString(TestStatus.FIRST))
        assertEquals(TestStatus.FIRST, mapper.readValue("\"F\"", TestStatus::class.java))
        assertThrows(Exception::class.java) {
            mapper.readValue("\"FIRST\"", TestStatus::class.java)
        }
    }
}
