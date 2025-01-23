package com.uliian.framework.oss.service

import com.qcloud.cos.COSClient
import com.qcloud.cos.ClientConfig
import com.qcloud.cos.auth.BasicCOSCredentials
import com.qcloud.cos.http.HttpMethodName
import com.qcloud.cos.region.Region
import com.uliian.framework.oss.IOssGateway
import com.uliian.framework.oss.config.OssConfig
import com.uliian.framework.oss.dto.UploadFileData
import com.uliian.framework.oss.service.AliyunOssImpl.Companion
import com.uliian.framework.oss.utils.MimeUtils
import com.uliian.idGenerate.EasyGenerator
import org.slf4j.LoggerFactory
import java.util.*

class TencentOssImpl(private val ossConfig: OssConfig, private val easyGenerator: EasyGenerator) :IOssGateway {
    companion object {
        val LOG = LoggerFactory.getLogger(TencentOssImpl::class.java)
    }
    val cosclient:COSClient
    init {
        AliyunOssImpl.LOG.info("TencentOssImpl initiated")
        val cred = BasicCOSCredentials(ossConfig.accessKeyId, ossConfig.accessKeySecret)
        // 2 设置的所属地域:我的是https://itimmortal-1319957111.cos.ap-chengdu.myqcloud.com
        val clientConfig =  ClientConfig()
        clientConfig.region =  Region(ossConfig.region)
        this.cosclient = COSClient(cred, clientConfig)
    }

    override fun getSign(savePrefix: String, fileName: String): UploadFileData {
        val fileExtName = MimeUtils.getSuffix(fileName, "jpg")

        // 你存储桶名称
        val bucketName = ossConfig.bucketName
        //对象键(Key)，使用UUID生成不重复的随机数，然后拼接文件后缀组成你前端上传到cos中的文件名
        val saveKey = "$savePrefix/${easyGenerator.generateIdResult().generateId()}.$fileExtName"
        //签名过期时间
        val expirationTime =  Date(System.currentTimeMillis() + 10 * 60 * 1000);
        // 生成预签名上传 URL
        //示例：https://itimmortal-1319957351.cos.ap-chengdu.myqcloud.com/0d3326e5-2272-4ee0-8445-e0ef5eec98fc.jpeg?sign=q-sign-algorithm%3Dsha1%26q-ak%3DAKIDxu111Pr9GBvUs1111lLm3Dj7PUBjIuXS%26q-sign-time%3D1691402050%3B1691111850%26q-key-time%3D169141150%3B169140111%26q-header-list%3Dhost%26q-url-param-list%3D%26q-signature%3D7d833d12f6e1668e260fa56edd0037f112e68111
        val url = cosclient.generatePresignedUrl(bucketName, saveKey, expirationTime, HttpMethodName.PUT, mutableMapOf(), mutableMapOf())

        return UploadFileData(
            saveKey,ossConfig.accessKeyId,"","",url.toString(),
            MimeUtils.getMime(fileExtName),ossConfig.downloadEndpoint,ossConfig.type
        )
    }
}