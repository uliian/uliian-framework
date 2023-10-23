package com.uliian.framework.web.defaultimpl

import com.uliian.framework.components.oplog.AppOpLog
import com.uliian.framework.components.oplog.IOpLogRepository
import org.slf4j.LoggerFactory

class OpLogDefaultRepository: IOpLogRepository {
    companion object{
        val LOG = LoggerFactory.getLogger(OpLogDefaultRepository::class.java)
    }

    init {
        LOG.warn("当前未实现 IOpLogRepository 接口，使用框架默认实现，在正式环境中必须自行实现这一接口")
    }

    override fun add(log: AppOpLog) {
        LOG.info("增加操作LOG：{}",log)
    }
}