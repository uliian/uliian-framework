package com.uliian.framework.mybatisplus.extention

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.uliian.framework.components.dto.AntdPageCondition
import com.uliian.framework.components.dto.PageCondition

fun <T> PageCondition.toPage(): IPage<T>{
    return this.toPage<T>(true)
}

fun  <T>  PageCondition.toPage(isSearchCount:Boolean): IPage<T>{
    return Page(this.index.toLong(), this.pageSize.toLong(),isSearchCount)
}
fun  <T>  AntdPageCondition.toPage(): IPage<T>{
    return this.toPage<T>(true)
}

fun  <T>  AntdPageCondition.toPage(isSearchCount:Boolean): IPage<T>{
    return Page(current.toLong(), pageSize.toLong(),isSearchCount)
}