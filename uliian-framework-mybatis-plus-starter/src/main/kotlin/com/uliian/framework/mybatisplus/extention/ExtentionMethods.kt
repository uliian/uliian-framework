package com.uliian.framework.mybatisplus.extention

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.IService
import com.uliian.framework.mybatisplus.OffsetPageResult
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty

fun <T : Any,K : Comparable<K>> IService<T>.offsetPage(condition: KtQueryWrapper<T>, keySelect: KMutableProperty1<T, K?>, offset: K, size: Int): OffsetPageResult<T,K> {
    val records = this.list(condition.gt(keySelect, offset).last("limit ${size + 1}"))
    return if (records.size > size) {
        val resultRecords = records.subList(0, size)
//        val newOffset = computeMaxOffset(resultRecords, keySelect)
        val newOffset = resultRecords.maxByOrNull { keySelect.call(it)!! }
        OffsetPageResult(resultRecords, true,keySelect.call(newOffset))
    } else {
        val newOffset = records.maxByOrNull { keySelect.call(it)!! }
        OffsetPageResult(records, false,keySelect.call(newOffset))
    }
}