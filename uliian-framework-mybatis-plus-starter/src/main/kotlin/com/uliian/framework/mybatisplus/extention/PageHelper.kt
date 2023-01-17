package com.uliian.framework.mybatisplus.extention

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page

class PageCondition {
    var index = 1
    var pageSize = 10

    fun <T> toPage(): IPage<T> {
        return this.toPage(true)
    }
    fun <T> toPage(isSearchCount:Boolean): IPage<T> {
        return Page(index.toLong(), pageSize.toLong(),isSearchCount)
    }
}
//
//class OffsetPageCondition {
//    var offset: Long = 0
//    var pageSize = 20
//    var orderBy: OrderType? = OrderType.Asc
//        get() = field ?: OrderType.Asc
//        set(value) {
//            field = value
//        }
//
//    fun <T> toPage(): IPage<T> {
//        return Page(1, pageSize + 1L,false)
//    }
//
//    fun limitSql(): String = " limit ${pageSize + 1}"
//}
//
//class OffsetPageResult<T, R : Comparable<R>>(condition: OffsetPageCondition, result: List<T>, keySelector: (T) -> R) {
//    val hasNext = result.size > condition.pageSize
//    val data = if (hasNext) result.subList(0, condition.pageSize) else result
//    val offset: R? = (if (condition.orderBy == OrderType.Desc) {
//        data.maxOfOrNull(keySelector)
//    } else data.minOfOrNull(keySelector))
//
//}
//
//class OffsetResultRsp<T>{
//    var hasNext:Boolean = false
//    var data:List<T> = listOf()
//    var offset:Long = 0
//}
