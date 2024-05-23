package com.uliian.framework.components.dto

data class AntdPage<T>(val data: List<T>, val success: Boolean, val total: Long){
    companion object {
        fun<T> emptyPage():AntdPage<T>{
            return AntdPage(emptyList(),true,0)
        }
    }
}