package com.uliian.framework.convert

interface IDescribeEnum<TCode: Number> {
    val code:TCode
    val description:String
}