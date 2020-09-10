package com.uliian.framework.core.convert

interface IDescribeEnum<TCode: Number> {
    val code:TCode
    val description:String
}