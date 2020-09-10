package com.uliian.framework.core.config

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator
import com.uliian.idGenerate.EasyGenerator
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Configuration
@ConfigurationProperties("framework.easy-generator")
@Component
class IdGeneratorConfig {
    companion object{
        val log = LoggerFactory.getLogger(IdGeneratorConfig::class.java)
    }

    var nodeId:Int = 1
    var timeWait:Int = 360

    @Bean
    fun initIdGenerator(): EasyGenerator {
        return EasyGenerator(this.nodeId,this.timeWait)
    }

    @Bean
    fun dbIdGenerator(easyGenerator: EasyGenerator): IdentifierGenerator {
        return IdentifierGenerator { easyGenerator.newId() }
    }
}