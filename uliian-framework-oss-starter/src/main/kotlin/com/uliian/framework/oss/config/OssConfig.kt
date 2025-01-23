package com.uliian.framework.oss.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("framework.oss")
class OssConfig {
    var type: String = "aliyun"
    var bucketName:String = ""
    var accessKeyId  = ""
    var accessKeySecret = ""
    var uploadEndpoint = ""
    var downloadEndpoint = ""
    var region:String? = ""
    var path = "/oss"
}