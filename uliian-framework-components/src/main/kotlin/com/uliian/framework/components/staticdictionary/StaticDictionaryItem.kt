package com.uliian.framework.components.staticdictionary

/**
 * Describes one value exposed by a static dictionary.
 */
interface StaticDictionaryItem {
    /** Stable machine-readable value used by business logic and APIs. */
    val value: String

    /** Display label. */
    val label: String

    /** Optional longer description. */
    val desc: String?

    /** Optional display order; null values are placed last. */
    val sort: Int?

    /**
     * Converts this item to its API DTO. Override this method to expose a
     * [StaticDictionaryOption] subclass with dictionary-specific fields.
     */
    fun toOption(): StaticDictionaryOption = StaticDictionaryOption(value, label, desc, sort)
}
