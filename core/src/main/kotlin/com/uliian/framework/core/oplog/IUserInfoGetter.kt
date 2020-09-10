package com.uliian.framework.core.oplog

import org.aspectj.lang.JoinPoint

interface IUserInfoGetter {
    fun getUserInfo(joinPoint: JoinPoint): IUserInfo
}

interface IUserInfo{
    val id:Long
    val name:String
}