package com.uliian.framework.components.config

import com.google.common.eventbus.EventBus
import com.uliian.framework.components.event.DeadEventHandler
import com.uliian.framework.components.event.IEventHandler
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EventBusConfig {
    @Bean
    @ConditionalOnMissingBean
    fun initEventbus(handlers:Collection<IEventHandler>): EventBus {
        val bus = EventBus()
        bus.register(DeadEventHandler())
        return bus
    }
}