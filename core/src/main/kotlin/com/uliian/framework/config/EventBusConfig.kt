package com.uliian.framework.config

import com.google.common.eventbus.EventBus
import com.uliian.framework.event.handler.DeadEventHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EventBusConfig {
    @Bean
    fun initEventbus(): EventBus {
        val bus = EventBus()
        bus.register(DeadEventHandler())
        return bus
    }
}