package com.uliian.framework.mybatisplus.extention

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService
import com.uliian.framework.mybatisplus.AntdPage
import com.uliian.framework.mybatisplus.OffsetPageResult
import kotlin.reflect.KMutableProperty1

fun <T : Any,K : Comparable<K>> IService<T>.offsetPage(condition: KtQueryWrapper<T>, keySelect: KMutableProperty1<T, K?>, offset: K, size: Int): OffsetPageResult<T,K> {
    val records = this.list(condition.gt(keySelect, offset).last("limit ${size + 1}"))
    return if (records.size > size) {
        val resultRecords = records.subList(0, size)
        val newOffset = resultRecords.maxByOrNull { keySelect.call(it)!! }
        OffsetPageResult(resultRecords, true,if(newOffset != null)keySelect.call(newOffset) else null)
    } else {
        val newOffset = records.maxByOrNull { keySelect.call(it)!! }
        OffsetPageResult(records, false,if(newOffset != null)keySelect.call(newOffset) else null)
    }
}

fun<T : Any,K : Comparable<K>,V> OffsetPageResult<T,K>.recordMap(transform: (T)->V): OffsetPageResult<V, K> {
    val records = this.records.map(transform)
    return OffsetPageResult(records,this.hasMore,this.offset)
}

fun<T,V> Page<T>.recordMap(transform: (T)->V): Page<V> {
    val records = this.records.map(transform)
    val result = Page<V>(this.current, this.size, this.total)
    result.records = records
    return result
}

fun<T,V> Page<T>.toAntdPage(transform: (T) -> V):AntdPage<V>{
    val records = this.records.map(transform)
    return AntdPage(records,true,this.total)
}

fun<T> Page<T>.toAntdPage():AntdPage<T>{
    return AntdPage(this.records,true,this.total)
}