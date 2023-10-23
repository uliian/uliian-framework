package com.uliian.framework.web.defaultimpl

import com.uliian.framework.components.oplog.IUserInfo
import com.uliian.framework.components.oplog.IUserInfoGetter
import org.aspectj.lang.JoinPoint
import org.slf4j.LoggerFactory

class DefaultUserInfoGetter : IUserInfoGetter {
    companion object{
        val log = LoggerFactory.getLogger(DefaultUserInfoGetter::class.java)
    }
    init {
        log.error("未实现 IUserInfoGetter ，操作日志记录为Mock数据，请自行实现IUserInfoGetter")
    }

    override fun getUserInfo(joinPoint: JoinPoint): IUserInfo {
        return object : IUserInfo{
            override val id: Long
                get() = -1
            override val name: String
                get() = "null"

        }
    }
}