package com.uliian.framework.mybatisplus.extention

import com.baomidou.mybatisplus.core.injector.AbstractMethod
import com.baomidou.mybatisplus.core.metadata.TableInfo
import org.apache.ibatis.executor.keygen.NoKeyGenerator
import org.apache.ibatis.mapping.MappedStatement
import java.lang.String


class MySqlInsertIgnore : AbstractMethod() {
    override fun injectMappedStatement(
        mapperClass: Class<*>,
        modelClass: Class<*>,
        tableInfo: TableInfo
    ): MappedStatement {
        val sql = "INSERT IGNORE INTO %s %s values %s"
        val fieldSql = StringBuilder()
        fieldSql.append(tableInfo.keyColumn).append(",")
        val valueSql = StringBuilder()
        valueSql.append("#{").append(tableInfo.keyProperty).append("},")
        tableInfo.fieldList.forEach { x ->
            fieldSql.append(x.column).append(",")
            valueSql.append("#{").append(x.property).append("},")
        }
        fieldSql.delete(fieldSql.length - 1, fieldSql.length)
        fieldSql.insert(0, "(")
        fieldSql.append(")")
        valueSql.insert(0, "(")
        valueSql.delete(valueSql.length - 1, valueSql.length)
        valueSql.append(")")
        val sqlSource = languageDriver.createSqlSource(
            configuration,
            String.format(sql, tableInfo.tableName, fieldSql.toString(), valueSql.toString()),
            modelClass
        )
        return addInsertMappedStatement(mapperClass, modelClass, "insertIgnore", sqlSource, NoKeyGenerator(), null, null)
    }

}