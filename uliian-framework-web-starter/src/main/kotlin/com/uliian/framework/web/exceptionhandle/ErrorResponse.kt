package com.uliian.framework.web.exceptionhandle

import com.uliian.framework.web.convert.IDescribeEnum
import org.springframework.http.HttpStatus

data class ErrorResponse(val code: ErrorCode, val msg:String)

enum class ErrorCode(override val code: Byte, override val description: String) : IDescribeEnum<Byte> {
    Normal(0,"普通错误"),
    SystemError(5,"系统异常")
}
