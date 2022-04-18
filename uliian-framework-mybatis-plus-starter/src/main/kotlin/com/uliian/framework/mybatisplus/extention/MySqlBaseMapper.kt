package com.uliian.framework.mybatisplus.extention

import com.baomidou.mybatisplus.core.mapper.BaseMapper

interface MySqlBaseMapper<T>:BaseMapper<T> {
    fun insertIgnore(entity:T):Int
}