package com.uliian.framework.oss.dto

data class UploadFileData(val key:String,val accessKey:String,val policy:String,val sign:String,val upload:String,
                          val mime:String,val downloadBasePath:String,val type:String)