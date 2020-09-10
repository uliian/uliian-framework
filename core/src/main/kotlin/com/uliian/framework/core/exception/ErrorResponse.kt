package com.uliian.framework.core.exception

import com.uliian.framework.core.convert.IDescribeEnum
import org.springframework.http.HttpStatus

data class ErrorResponse(val code: ErrorCode, val msg:String)

enum class ErrorCode(override val code: Byte, override val description: String) : IDescribeEnum<Byte> {
    Normal(0,"普通错误"),
    SystemError(5,"系统异常")
}

open class AppException(val httpStatus: HttpStatus, msg:String): RuntimeException(msg)
