package com.uliian.framework.oss.service

import com.google.common.io.BaseEncoding
import com.uliian.framework.oss.IOssGateway
import com.uliian.framework.oss.config.OssConfig
import com.uliian.framework.oss.dto.UploadFileData
import com.uliian.framework.oss.utils.MimeUtils
import com.uliian.idGenerate.EasyGenerator
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class AliyunOssImpl(private val ossConfig: OssConfig, private val easyGenerator: EasyGenerator) :IOssGateway {
    companion object {
        val LOG = LoggerFactory.getLogger(AliyunOssImpl::class.java)
    }
    init {
        LOG.info("AliyunOssImpl initiated")
    }

    override fun getSign(savePrefix: String, fileName: String): UploadFileData {
        val fileExtName = MimeUtils.getSuffix(fileName)
        val saveKey = "$savePrefix/${easyGenerator.generateIdResult().generateId()}.$fileExtName"
        val expireOn = LocalDateTime.now().plusMinutes(120)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .withZone(ZoneId.of("UTC"))
        val bucketName = ossConfig.bucketName

        val policy = """
            { "expiration": "${expireOn.format(formatter)}",
              "conditions": [
                {"bucket": "$bucketName" },
                {"key":"$saveKey"}
              ]
            }
        """.trimIndent()
        val encodePolicy = BaseEncoding.base64().encode(policy.toByteArray(Charsets.UTF_8))
        val sign = computeSignature(ossConfig.accessKeySecret, encodePolicy)

        return UploadFileData(
            saveKey,
            ossConfig.accessKeyId,
            encodePolicy,
            sign,
            ossConfig.uploadEndpoint,
            MimeUtils.getMime(fileExtName),
            ossConfig.downloadEndpoint,
            ossConfig.type
        )
    }


    private fun computeSignature(accessKeySecret: String, policy: String): String {
        val key: ByteArray = accessKeySecret.toByteArray(Charsets.UTF_8)
        val data: ByteArray = policy.toByteArray(Charsets.UTF_8)

        // hmac-sha1
        val mac: Mac = Mac.getInstance("HmacSHA1")
        mac.init(SecretKeySpec(key, "HmacSHA1"))
        val sha: ByteArray = mac.doFinal(data)

        // base64
        return BaseEncoding.base64().encode(sha)
    }
}