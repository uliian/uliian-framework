package com.uliian.framework.web.config

import com.uliian.framework.components.oplog.IOpLogRepository
import com.uliian.framework.components.oplog.IUserInfoGetter
import com.uliian.framework.web.defaultimpl.DefaultUserInfoGetter
import com.uliian.framework.web.defaultimpl.OpLogDefaultRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.AutoConfigureOrder
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@AutoConfigureOrder(-1)
class BizLoggerConfiguration() {

    companion object{
        val log = LoggerFactory.getLogger(BizLoggerConfiguration::class.java)
    }
    init {
        log.info("Init Framework!!")
    }

    @Bean
    @ConditionalOnMissingBean(IOpLogRepository::class)
    fun initOpLogRepository()= OpLogDefaultRepository()

    @Bean
    @ConditionalOnMissingBean(IUserInfoGetter::class)
    fun initUserInfoGetter() = DefaultUserInfoGetter()
}