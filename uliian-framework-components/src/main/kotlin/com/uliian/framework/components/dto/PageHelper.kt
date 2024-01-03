package com.uliian.framework.components.dto


class PageCondition {
    var index = 1
    var pageSize = 10

    override fun toString(): String {
        return "PageCondition(index=$index, pageSize=$pageSize)"
    }
}

class AntdPageCondition{
    var current = 1
    var pageSize = 10
}
