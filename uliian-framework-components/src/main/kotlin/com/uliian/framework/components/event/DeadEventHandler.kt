package com.uliian.framework.components.event

import com.google.common.eventbus.DeadEvent
import com.google.common.eventbus.Subscribe
import org.slf4j.LoggerFactory

class DeadEventHandler {
    companion object {
        val log = LoggerFactory.getLogger(DeadEventHandler::class.java)
    }

    @Subscribe
    fun deadEventHandle(deadEvent: DeadEvent) {
        log.warn("the event ${deadEvent.event} is dead event")
    }
}