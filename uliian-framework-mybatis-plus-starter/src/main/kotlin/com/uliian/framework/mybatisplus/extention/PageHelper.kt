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

    override fun toString(): String {
        return "PageCondition(index=$index, pageSize=$pageSize)"
    }
}

class AntdPageCondition{
    var current = 1
    var pageSize = 10

    fun <T> toPage(): IPage<T> {
        return this.toPage(true)
    }
    fun <T> toPage(isSearchCount:Boolean): IPage<T> {
        return Page(current.toLong(), pageSize.toLong(),isSearchCount)
    }
}
