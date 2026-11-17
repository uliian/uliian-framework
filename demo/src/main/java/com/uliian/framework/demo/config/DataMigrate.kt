package com.uliian.framework.demo.config

import com.baomidou.mybatisplus.spring.ddl.SimpleDdl
import org.slf4j.LoggerFactory
import org.springframework.core.io.support.ResourcePatternResolver
import org.springframework.stereotype.Component

@Component
class DataMigrate(private val resourcePatternResolver: ResourcePatternResolver) : SimpleDdl() {
    companion object {
        val LOG = LoggerFactory.getLogger(DataMigrate::class.java)
    }
    override fun getSqlFiles(): MutableList<String> {
        val locationPattern = "classpath*:db/*.sql"
        val resources = resourcePatternResolver.getResources(locationPattern)
        val ttt = resources.map {
             it.url.path
             }.toMutableList()
        LOG.info("DataMigrate getSqlFiles:{}", ttt)
        return ttt
    }
}
