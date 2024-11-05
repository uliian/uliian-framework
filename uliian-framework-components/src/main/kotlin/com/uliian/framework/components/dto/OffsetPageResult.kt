package com.uliian.framework.components.dto

data class OffsetPageResult<T,K : Comparable<K>>(val records: List<T>, val hasMore: Boolean, val offset: K?){
    companion object{
        fun <V,K: Comparable<K>> emptyResult(defaultKey:K):OffsetPageResult<V,K>{
            return OffsetPageResult(emptyList(),false,defaultKey)
        }
    }

    fun <V> recordMap(transform: (T) -> V): OffsetPageResult<V,K> {
        val records = this.records.map(transform)
        return OffsetPageResult(records, this.hasMore, this.offset)
    }
}

enum class OrderType {
    Asc,Desc
}