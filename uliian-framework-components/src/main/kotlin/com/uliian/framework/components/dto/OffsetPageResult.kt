package com.uliian.framework.components.dto

data class OffsetPageResult<T,K : Comparable<K>>(val records: List<T>, val hasMore: Boolean, val offset: K?)

enum class OrderType {
    Asc,Desc
}