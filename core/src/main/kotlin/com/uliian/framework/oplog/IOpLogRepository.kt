package com.uliian.framework.oplog

import org.slf4j.LoggerFactory

interface IOpLogRepository {
    companion object{
        val log = LoggerFactory.getLogger(IOpLogRepository::class.java)
    }
    fun add(log: AppOpLog)
}