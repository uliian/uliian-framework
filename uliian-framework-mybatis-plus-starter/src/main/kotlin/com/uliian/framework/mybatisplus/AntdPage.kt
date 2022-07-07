package com.uliian.framework.mybatisplus

data class AntdPage<T>(val data: List<T>, val success: Boolean, val total: Long)