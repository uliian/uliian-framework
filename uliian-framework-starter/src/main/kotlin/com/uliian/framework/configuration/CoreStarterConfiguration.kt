package com.uliian.framework.configuration

import com.uliian.framework.core.oplog.IOpLogRepository
import com.uliian.framework.core.oplog.IUserInfoGetter
import com.uliian.framework.defaultimpl.DefaultUserInfoGetter
import com.uliian.framework.defaultimpl.OpLogDefaultRepository
import com.uliian.idGenerate.EasyGenerator
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["com.uliian.framework.core"])
class CoreStarterConfiguration() {
    companion object{
        val log = LoggerFactory.getLogger(CoreStarterConfiguration::class.java)
    }

    @Bean
    @ConditionalOnMissingBean(IOpLogRepository::class)
    fun initOpLogRepository()=OpLogDefaultRepository()

    @Bean
    @ConditionalOnMissingBean(IUserInfoGetter::class)
    fun initUserInfoGetter() = DefaultUserInfoGetter()
}