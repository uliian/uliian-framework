package com.uliian.framework.components.oplog

import org.aspectj.lang.JoinPoint

interface IUserInfoGetter {
    fun getUserInfo(joinPoint: JoinPoint): IUserInfo
}

interface IUserInfo{
    val id:Long
    val name:String
}