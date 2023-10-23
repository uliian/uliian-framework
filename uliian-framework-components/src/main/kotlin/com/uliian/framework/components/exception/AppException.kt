package com.uliian.framework.components.exception

open class AppException(val httpStatus: Int, msg:String): RuntimeException(msg)