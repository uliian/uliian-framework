package com.uliian.framework.oss.service

import com.qiniu.util.Auth
import com.qiniu.util.StringMap
import com.uliian.framework.oss.IOssGateway
import com.uliian.framework.oss.config.OssConfig
import com.uliian.framework.oss.dto.UploadFileData
import com.uliian.framework.oss.utils.MimeUtils
import com.uliian.idGenerate.EasyGenerator

class QiniuOssImpl(private val ossConfig: OssConfig, private val easyGenerator: EasyGenerator) :IOssGateway{
    override fun getSign(savePrefix: String, fileName: String): UploadFileData {
        val auth = Auth.create(ossConfig.accessKeyId, ossConfig.accessKeySecret)
        val policy = StringMap()
        val fileExtName = MimeUtils.getSuffix(fileName, "jpg")
        val saveKey = "$savePrefix/${easyGenerator.newId()}.$fileExtName"
        policy.put("saveKey", saveKey)
        val result = auth.uploadToken(ossConfig.bucketName, saveKey, 600, policy)
        return UploadFileData(
            saveKey,
            ossConfig.accessKeyId,
            result,
            "",
            ossConfig.uploadEndpoint,
            MimeUtils.getMime(fileExtName),
            ossConfig.downloadEndpoint,
            ossConfig.type
        )
    }
}