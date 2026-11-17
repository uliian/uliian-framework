package com.uliian.framework.web.controller

import com.uliian.framework.components.staticdictionary.StaticDictionaryOption
import com.uliian.framework.components.staticdictionary.StaticDictionaryRegistry
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/** Read-only API for frontend static dictionary options. */
@RestController
@RequestMapping("/static-dictionaries")
class StaticDictionaryController(
    private val registry: StaticDictionaryRegistry
) {
    /** Returns the options registered for [type] as a bare list. */
    @GetMapping("/{type}")
    fun get(@PathVariable type: String): List<StaticDictionaryOption> = registry.get(type)
}
