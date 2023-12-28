package com.uliian.framework.components.dto

data class AntdPage<T>(val data: List<T>, val success: Boolean, val total: Long)