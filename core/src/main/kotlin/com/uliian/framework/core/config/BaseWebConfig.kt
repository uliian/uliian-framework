package com.uliian.framework.core.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.uliian.framework.core.convert.LocalDateTimeDeserializerExt
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.math.BigInteger
import java.nio.charset.Charset
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@EnableWebMvc
@Configuration
class BaseWebConfig(private val objectMapper: ObjectMapper) : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) { //添加映射路径
        registry.addMapping("/**") //放行哪些原始域
                .allowedOriginPatterns("*") //是否发送Cookie信息
                .allowCredentials(true) //放行哪些原始域(请求方式)
                .allowedMethods("GET", "POST", "PUT", "DELETE") //放行哪些原始域(头部信息)
                .allowedHeaders("*") //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
    }

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>?>) {
        converters.add(StringHttpMessageConverter(Charset.forName("utf-8")))

        val simpleModule = SimpleModule()
        simpleModule.addSerializer(Long::class.java, ToStringSerializer.instance)
        simpleModule.addSerializer(java.lang.Long.TYPE, ToStringSerializer.instance)
        simpleModule.addSerializer(BigInteger::class.java, ToStringSerializer.instance)
        simpleModule.addSerializer(kotlin.Long::class.java, ToStringSerializer.instance)
        simpleModule.addSerializer(java.lang.Long::class.java, ToStringSerializer.instance)
        simpleModule.addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        simpleModule.addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializerExt())

        this.objectMapper.registerModule(simpleModule)
        val mappingJackson2HttpMessageConverter = MappingJackson2HttpMessageConverter(this.objectMapper)
        val supportedMediaTypes = mappingJackson2HttpMessageConverter.supportedMediaTypes
        //        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        val mediaTypes = ArrayList(supportedMediaTypes)
        mediaTypes.add(MediaType.APPLICATION_XML)
        mappingJackson2HttpMessageConverter.supportedMediaTypes = mediaTypes
        converters.add(mappingJackson2HttpMessageConverter)
    }
}