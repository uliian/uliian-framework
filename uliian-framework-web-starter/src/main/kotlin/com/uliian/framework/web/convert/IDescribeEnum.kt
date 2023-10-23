package com.uliian.framework.web.convert

interface IDescribeEnum<TCode: Number> {
    val code:TCode
    val description:String
}