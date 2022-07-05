package com.uliian.framework.mybatisplus.extention

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.core.enums.SqlMethod
import com.baomidou.mybatisplus.core.injector.AbstractMethod
import com.baomidou.mybatisplus.core.metadata.TableInfo
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper
import com.baomidou.mybatisplus.core.toolkit.StringUtils
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator
import org.apache.ibatis.executor.keygen.KeyGenerator
import org.apache.ibatis.executor.keygen.NoKeyGenerator
import org.apache.ibatis.mapping.MappedStatement


class MySqlInsertIgnore : AbstractMethod() {
//    override fun injectMappedStatement(
//        mapperClass: Class<*>,
//        modelClass: Class<*>,
//        tableInfo: TableInfo
//    ): MappedStatement {
//        val sql = "INSERT IGNORE INTO %s %s values %s"
//        val fieldSql = StringBuilder()
//        fieldSql.append(tableInfo.keyColumn).append(",")
//        val valueSql = StringBuilder()
//        valueSql.append("#{").append(tableInfo.keyProperty).append("},")
//        tableInfo.fieldList.forEach { x ->
//            fieldSql.append(x.column).append(",")
//            valueSql.append("#{").append(x.property).append("},")
//        }
//        fieldSql.delete(fieldSql.length - 1, fieldSql.length)
//        fieldSql.insert(0, "(")
//        fieldSql.append(")")
//        valueSql.insert(0, "(")
//        valueSql.delete(valueSql.length - 1, valueSql.length)
//        valueSql.append(")")
//        val sqlSource = languageDriver.createSqlSource(
//            configuration,
//            String.format(sql, tableInfo.tableName, fieldSql.toString(), valueSql.toString()),
//            modelClass
//        )
//        return addInsertMappedStatement(mapperClass, modelClass, "insertIgnore", sqlSource, NoKeyGenerator(), null, null)
//    }

    override fun injectMappedStatement(
        mapperClass: Class<*>?,
        modelClass: Class<*>?,
        tableInfo: TableInfo?
    ): MappedStatement {
        var keyGenerator: KeyGenerator? = NoKeyGenerator()
        val sqlMethod = SqlMethod.INSERT_ONE
        val columnScript = SqlScriptUtils.convertTrim(
            tableInfo!!.getAllInsertSqlColumnMaybeIf(null),
            LEFT_BRACKET, RIGHT_BRACKET, null, COMMA
        )
        val valuesScript = SqlScriptUtils.convertTrim(
            tableInfo!!.getAllInsertSqlPropertyMaybeIf(null),
            LEFT_BRACKET, RIGHT_BRACKET, null, COMMA
        )
        var keyProperty: kotlin.String? = null
        var keyColumn: kotlin.String? = null
        // 表包含主键处理逻辑,如果不包含主键当普通字段处理
        // 表包含主键处理逻辑,如果不包含主键当普通字段处理
        if (StringUtils.isNotBlank(tableInfo!!.keyProperty)) {
            if (tableInfo!!.idType == IdType.AUTO) {
                /** 自增主键  */
                keyGenerator = Jdbc3KeyGenerator()
                keyProperty = tableInfo!!.keyProperty
                keyColumn = tableInfo!!.keyColumn
            } else {
                if (null != tableInfo!!.keySequence) {
                    keyGenerator = TableInfoHelper.genKeyGenerator(getMethod(sqlMethod), tableInfo, builderAssistant)
                    keyProperty = tableInfo!!.keyProperty
                    keyColumn = tableInfo.keyColumn
                }
            }
        }
        val sqlTemplate = "<script>\nINSERT IGNORE INTO %s %s VALUES %s\n</script>"
        val sql = String.format(sqlTemplate, tableInfo.tableName, columnScript, valuesScript)
        val sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass)
        return addInsertMappedStatement(
            mapperClass,
            modelClass,
            "insertIgnore",
            sqlSource,
            keyGenerator,
            keyProperty,
            keyColumn
        )
    }

}