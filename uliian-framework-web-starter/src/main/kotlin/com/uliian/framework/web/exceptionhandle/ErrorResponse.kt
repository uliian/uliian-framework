package com.uliian.framework.web.exceptionhandle

import com.uliian.framework.web.convert.IDescribeEnum
import org.springframework.http.HttpStatus

data class ErrorResponse(val code: Int, val msg:String)

enum class ErrorCode(override val code: Int, override val description: String) : IDescribeEnum<Int> {
    Normal(0,"普通错误"),
    SystemError(5,"系统异常")
}
