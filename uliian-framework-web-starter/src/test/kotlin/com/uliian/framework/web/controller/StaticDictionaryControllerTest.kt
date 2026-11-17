package com.uliian.framework.web.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.uliian.framework.components.staticdictionary.StaticDictionary
import com.uliian.framework.components.staticdictionary.StaticDictionaryItem
import com.uliian.framework.components.staticdictionary.StaticDictionaryRegistry
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@StaticDictionary("web-test")
private enum class WebTestDictionary(
    override val value: String,
    override val label: String,
    override val desc: String?,
    override val sort: Int?
) : StaticDictionaryItem {
    ENABLED("enabled", "Enabled", null, 10)
}

class StaticDictionaryControllerTest {
    @Test
    fun `returns a bare option list`() {
        val registry = StaticDictionaryRegistry(listOf(javaClass.packageName))
        val result = StaticDictionaryController(registry).get("web-test")

        assertEquals("[{\"value\":\"enabled\",\"label\":\"Enabled\",\"desc\":null,\"sort\":10}]",
            ObjectMapper().writeValueAsString(result))
    }
}
