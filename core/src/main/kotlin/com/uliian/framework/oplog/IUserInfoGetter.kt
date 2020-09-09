package com.uliian.framework.oplog

import org.aspectj.lang.JoinPoint

interface IUserInfoGetter {
    fun getUserInfo(joinPoint: JoinPoint):IUserInfo
}

interface IUserInfo{
    val id:Long
    val name:String
}