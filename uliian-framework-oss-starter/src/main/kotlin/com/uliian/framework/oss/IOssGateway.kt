package com.uliian.framework.oss

import com.uliian.framework.oss.dto.UploadFileData

interface IOssGateway {
    fun getSign(savePrefix: String, fileName: String): UploadFileData
}