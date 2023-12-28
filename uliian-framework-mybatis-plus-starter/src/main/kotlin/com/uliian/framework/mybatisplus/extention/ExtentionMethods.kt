package com.uliian.framework.mybatisplus.extention

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.support.SFunction
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.uliian.framework.components.dto.AntdPage
import com.uliian.framework.components.dto.OffsetPageResult
import com.uliian.framework.components.dto.OrderType
import kotlin.reflect.KMutableProperty1

/**
 * 在使用时，第一页需要注意offset，在orderType = desc时，offset为max(typeof(offset)),需要在应用中自己处理好
 */
fun <T : Any, K : Comparable<K>> BaseMapper<T>.offsetPage(
    condition: KtQueryWrapper<T>,
    keySelect: KMutableProperty1<T, K?>,
    orderType: OrderType,
    offset: K?,
    size: Int
): OffsetPageResult<T, K> {
    val newCondition = if(orderType == OrderType.Desc){
        condition.lt(offset!= null, keySelect,offset).orderByDesc(keySelect)
    }else{
        condition.gt(offset!= null,keySelect,offset).orderByAsc(keySelect)
    }
    val records = this.selectList(newCondition.last("limit ${size + 1}"))

    return if (records.size > size) {
        val resultRecords = records.subList(0, size)
        val newOffset =
            if (orderType == OrderType.Asc) resultRecords.maxOfOrNull { keySelect.call(it)!! } else resultRecords.minOfOrNull {
                keySelect.call(it)!!
            }
        OffsetPageResult(resultRecords, true, newOffset)
    } else {
        val newOffset =
            if (orderType == OrderType.Asc) records.maxOfOrNull { keySelect.call(it)!! } else records.minOfOrNull {
                keySelect.call(it)!!
            }
        OffsetPageResult(records, false, newOffset)
    }
}

fun <T : Any, K : Comparable<K>> BaseMapper<T>.offsetPage(
    condition: LambdaQueryWrapper<T>,
    keySelect: SFunction<T, K?>,
    orderType: OrderType,
    offset: K?,
    size: Int
): OffsetPageResult<T, K> {
    val newCondition = if(orderType == OrderType.Desc){
        condition.lt(offset!= null, keySelect,offset).orderByDesc(keySelect)
    }else{
        condition.gt(offset!= null,keySelect,offset).orderByAsc(keySelect)
    }
    val records = this.selectList(newCondition.last("limit ${size + 1}"))

    return if (records.size > size) {
        val resultRecords = records.subList(0, size)
        val newOffset =
            if (orderType == OrderType.Asc) resultRecords.maxOfOrNull { keySelect.apply(it)!! } else resultRecords.minOfOrNull {
                keySelect.apply(it)!!
            }
        OffsetPageResult(resultRecords, true, newOffset)
    } else {
        val newOffset =
            if (orderType == OrderType.Asc) records.maxOfOrNull { keySelect.apply(it)!! } else records.minOfOrNull {
                keySelect.apply(it)!!
            }
        OffsetPageResult(records, false, newOffset)
    }
}

fun <T : Any, K : Comparable<K>, V> OffsetPageResult<T, K>.recordMap(transform: (T) -> V): OffsetPageResult<V, K> {
    val records = this.records.map(transform)
    return OffsetPageResult(records, this.hasMore, this.offset)
}

fun <T, V> IPage<T>.recordMap(transform: (T) -> V): Page<V> {
    val records = this.records.map(transform)
    val result = Page<V>(this.current, this.size, this.total)
    result.records = records
    return result
}

fun <T, V> IPage<T>.toAntdPage(transform: (T) -> V): AntdPage<V> {
    val records = this.records.map(transform)
    return AntdPage(records, true, this.total)
}

fun <T> IPage<T>.toAntdPage(): AntdPage<T> {
    return AntdPage(this.records, true, this.total)
}