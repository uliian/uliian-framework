package com.uliian.framework.mybatisplus.config

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator
import com.uliian.framework.core.config.IdGeneratorConfig
import com.uliian.framework.mybatisplus.extention.MySqlInjector
import com.uliian.idGenerate.EasyGenerator
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
class MyBatisPlusConfig {
    companion object{
        val LOG = LoggerFactory.getLogger(MyBatisPlusConfig::class.java)
    }

    init {
        LOG.info("Init MyBatisPlus Framework!!")
    }
    @Bean
    fun mysqlInject() = MySqlInjector()

    @Bean
    @ConditionalOnBean(EasyGenerator::class)
    fun dbIdGenerator(easyGenerator: EasyGenerator): IdentifierGenerator{
        val result = easyGenerator.generateIdResult()
        LOG.info("初始化MP ID生成器：EasyGenerator：nodeId:{}",result.nodeId)
        return IdentifierGenerator { easyGenerator.newId() }
    }
}