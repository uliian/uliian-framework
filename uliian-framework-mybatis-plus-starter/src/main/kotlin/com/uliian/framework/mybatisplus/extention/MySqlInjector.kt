package com.uliian.framework.mybatisplus.extention

import com.baomidou.mybatisplus.core.injector.AbstractMethod
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector

class MySqlInjector: DefaultSqlInjector() {
    override fun getMethodList(mapperClass: Class<*>?): MutableList<AbstractMethod> {
        val methodList = super.getMethodList(mapperClass)
        methodList.add(MySqlInsertIgnore())
        return methodList
    }
}