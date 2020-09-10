package com.uliian.framework.core.utils

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page

enum class OrderType {
    Asc, Desc
}

class PageCondition {
    var index = 1
    var pageSize = 10

    fun <T> toPage(): IPage<T> {
        return Page(1, pageSize.toLong())
    }
}

class OffsetPageCondition {
    var offset: Long = 0
    var pageSize = 20
    var orderBy: OrderType? = OrderType.Asc
        get() = field ?: OrderType.Asc
        set(value) {
            field = value
        }

    fun <T> toPage(): IPage<T> {
        return Page(1, pageSize + 1L,false)
    }

    fun limitSql(): String = " limit ${pageSize + 1}"
}

class OffsetPageResult<T, R : Comparable<R>>(condition: OffsetPageCondition, result: List<T>, keySelector: (T) -> R) {
    val hasNext = result.size > condition.pageSize
    val data = if (hasNext) result.subList(0, condition.pageSize) else result
    val offset: Any = (if (condition.orderBy == OrderType.Desc) data.map(keySelector).max() else data.map(keySelector).min())
            ?: 0
}

class OffsetResultRsp<T>{
    var hasNext:Boolean = false
    var data:List<T> = listOf()
    var offset:Long = 0
}
