package com.uliian.framework.configuration

import com.uliian.framework.core.config.IdGeneratorConfig
import com.uliian.framework.core.oplog.IOpLogRepository
import com.uliian.framework.core.oplog.IUserInfoGetter
import com.uliian.framework.defaultimpl.DefaultUserInfoGetter
import com.uliian.framework.defaultimpl.OpLogDefaultRepository
import com.uliian.idGenerate.EasyGenerator
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.AutoConfigureOrder
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@ComponentScan(basePackages = ["com.uliian.framework.core"])
@Import(IdGeneratorConfig::class)
@AutoConfigureOrder(-1)
class CoreStarterConfiguration() {

    companion object{
        val log = LoggerFactory.getLogger(CoreStarterConfiguration::class.java)
    }
    init {
        log.info("Init Framework!!")
    }

    @Bean
    @ConditionalOnMissingBean(IOpLogRepository::class)
    fun initOpLogRepository()=OpLogDefaultRepository()

    @Bean
    @ConditionalOnMissingBean(IUserInfoGetter::class)
    fun initUserInfoGetter() = DefaultUserInfoGetter()


}