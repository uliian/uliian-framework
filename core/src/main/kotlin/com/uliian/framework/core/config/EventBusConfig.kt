package com.uliian.framework.core.config

import com.google.common.eventbus.EventBus
import com.uliian.framework.core.event.handler.DeadEventHandler
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EventBusConfig {
    @Bean
    @ConditionalOnMissingBean
    fun initEventbus(): EventBus {
        val bus = EventBus()
        bus.register(DeadEventHandler())
        return bus
    }
}