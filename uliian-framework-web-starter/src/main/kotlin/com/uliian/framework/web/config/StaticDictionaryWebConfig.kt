package com.uliian.framework.web.config

import com.uliian.framework.components.staticdictionary.StaticDictionaryRegistry
import com.uliian.framework.web.controller.StaticDictionaryController
import org.springframework.beans.factory.BeanFactory
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfigurationPackages
import org.springframework.context.annotation.Bean

/** Auto-configures static dictionary discovery and its read-only endpoint. */
@AutoConfiguration
class StaticDictionaryWebConfig {
    @Bean
    fun staticDictionaryRegistry(beanFactory: BeanFactory): StaticDictionaryRegistry =
        StaticDictionaryRegistry(AutoConfigurationPackages.get(beanFactory))

    @Bean
    fun staticDictionaryController(registry: StaticDictionaryRegistry): StaticDictionaryController =
        StaticDictionaryController(registry)
}
