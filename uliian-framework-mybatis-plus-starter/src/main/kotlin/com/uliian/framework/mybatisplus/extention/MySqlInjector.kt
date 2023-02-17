package com.uliian.framework.mybatisplus.extention

import com.baomidou.mybatisplus.core.injector.AbstractMethod
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector
import com.baomidou.mybatisplus.core.metadata.TableInfo

class MySqlInjector: DefaultSqlInjector() {
    override fun getMethodList(mapperClass: Class<*>?, tableInfo: TableInfo?): MutableList<AbstractMethod> {
        val methodList = super.getMethodList(mapperClass,tableInfo)
        methodList.add(MySqlInsertIgnore("InsertIgnore"))
        return methodList
    }
}