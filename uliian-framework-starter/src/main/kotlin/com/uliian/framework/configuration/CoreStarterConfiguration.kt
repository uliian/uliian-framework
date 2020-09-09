package com.uliian.framework.configuration

import com.uliian.idGenerate.EasyGenerator
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["com.uliian.framework.config"])
open class CoreStarterConfiguration() {
    companion object{
        val log = LoggerFactory.getLogger(CoreStarterConfiguration::class.java)
    }

    init {
        log.info("test!!!")
    }
//
//    @Bean
//    @ConditionalOnMissingBean(EasyGenerator::class)
//    fun initIDg()=EasyGenerator(1,1000)
}