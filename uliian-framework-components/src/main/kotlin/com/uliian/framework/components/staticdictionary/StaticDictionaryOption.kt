package com.uliian.framework.components.staticdictionary

/**
 * Common DTO returned for a static dictionary item.
 *
 * This class is open so a dictionary can add frontend metadata in a subclass.
 */
open class StaticDictionaryOption(
    val value: String,
    val label: String,
    val desc: String?,
    val sort: Int?
) {
    companion object {
        /** Uses the item's conversion method and preserves custom DTO subtypes. */
        @JvmStatic
        fun from(item: StaticDictionaryItem): StaticDictionaryOption = item.toOption()
    }

    override fun equals(other: Any?): Boolean =
        this === other || other is StaticDictionaryOption &&
            value == other.value && label == other.label && desc == other.desc && sort == other.sort

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + label.hashCode()
        result = 31 * result + (desc?.hashCode() ?: 0)
        result = 31 * result + (sort ?: 0)
        return result
    }

    override fun toString(): String =
        "StaticDictionaryOption(value='$value', label='$label', desc=$desc, sort=$sort)"
}
