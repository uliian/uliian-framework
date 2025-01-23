package com.uliian.framework.oss.config

import com.uliian.framework.oss.IOssGateway
import com.uliian.framework.oss.controller.OssController
import com.uliian.framework.oss.service.AliyunOssImpl
import com.uliian.framework.oss.service.QiniuOssImpl
import com.uliian.framework.oss.service.TencentOssImpl
import com.uliian.idGenerate.EasyGenerator
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OssModuleConfig {
    @Bean
    fun initConfig() = OssConfig()

    @ConditionalOnMissingBean(IOssGateway::class)
    @Bean()
    fun initOssService(ossConfig: OssConfig,easyGenerator: EasyGenerator): IOssGateway {
        when(ossConfig.type){
            "aliyun" -> {
                return AliyunOssImpl(ossConfig,easyGenerator)
            }
            "tencent" -> {
                return TencentOssImpl(ossConfig,easyGenerator)
            }
            "qiniu" -> {
                return QiniuOssImpl(ossConfig,easyGenerator)
            }
            else -> {
                throw IllegalArgumentException("不支持的OSS类型")
            }
        }
    }

    @Bean
    fun initController(gateway: IOssGateway) = OssController(gateway)
}