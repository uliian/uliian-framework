package com.uliian.framework.components.event

import com.google.common.eventbus.EventBus
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import java.util.function.Consumer

@Component
class HandlerAutoRegister : ApplicationListener<ContextRefreshedEvent> {
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        val applicationContext = event.applicationContext
        val type = applicationContext.getBeansOfType(IEventHandler::class.java)
        val bus = applicationContext.getBean(EventBus::class.java)
        type.values.forEach(Consumer { x: IEventHandler? -> bus.register(x) })
    }
}