package com.uliian.framework.demo.enums

import com.uliian.framework.components.staticdictionary.StaticDictionary
import com.uliian.framework.components.staticdictionary.StaticDictionaryItem

/** Demo static dictionary used to verify discovery and API output. */
@StaticDictionary("test-static-dictionary")
enum class TestStaticDictionary(
    override val value: String,
    override val label: String,
    override val desc: String?,
    override val sort: Int?
) : StaticDictionaryItem {
    ENABLED("enabled", "启用", "当前处于启用状态", 10),
    DISABLED("disabled", "禁用", "当前处于禁用状态", 20),
    PENDING("pending", "待处理", "等待后续处理", 30)
}
