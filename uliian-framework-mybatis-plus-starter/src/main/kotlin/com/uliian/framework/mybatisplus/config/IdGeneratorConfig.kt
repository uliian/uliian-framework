package com.uliian.framework.mybatisplus.config

import com.uliian.framework.core.exception.AppException
import com.uliian.framework.core.utils.NetworkUtils
import com.uliian.framework.core.utils.SubnetUtils
import com.uliian.idGenerate.EasyGenerator
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.AutoConfigureOrder
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Configuration
@ConfigurationProperties("framework.easy-generator")
@Component
@AutoConfigureOrder(0)
class IdGeneratorConfig {
    companion object {
        val log = LoggerFactory.getLogger(IdGeneratorConfig::class.java)
    }

    var nodeId: Int? = null
    var timeWait: Int = 360
    var cidr: String? = null

    @Bean
    @ConditionalOnMissingBean
    @Order(1)
    @Primary
    fun initIdGenerator(): EasyGenerator {
        val nid :Int = if (this.cidr!=null){
            generateNodeIdFromCidr()
        }else{
            this.nodeId
        } ?: throw AppException(HttpStatus.INTERNAL_SERVER_ERROR,"无法生成NodeId")
        return EasyGenerator(nid, this.timeWait)
    }

    fun generateNodeIdFromCidr(): Int {
        log.info("已经设置了CIDR，根据CIDR生成NodeID")
        val utils = SubnetUtils(cidr)
        val localIp = NetworkUtils.getIpv4Ips().first { utils.getInfo().isInRange(it) }
        log.info("根据CIDR，获取到IP $localIp 用于生成的NodeID")
        val ipInteger = utils.getInfo().asInteger(localIp)
        val revertMask = utils.netmask.inv()
        val nodeId = (ipInteger and revertMask) % 1024
        log.info("NodeId:$nodeId")
        return nodeId
    }
}