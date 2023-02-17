package com.uliian.framework.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.uliian.framework.mybatisplus.convert.EnhanceStringToEnumConverterFactory
import com.uliian.framework.mybatisplus.convert.EnumDeserializer
import com.uliian.framework.mybatisplus.convert.EnumSerializer
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@EnableWebMvc
@Configuration
class MyBatisPlusWebConfig(private val objectMapper: ObjectMapper) : WebMvcConfigurer {

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverterFactory(EnhanceStringToEnumConverterFactory())
        val enumModule = SimpleModule("enum-module")
        enumModule.addDeserializer(Enum::class.java, EnumDeserializer())
        enumModule.addSerializer(Enum::class.java, EnumSerializer)
        objectMapper.registerModule(enumModule)
    }
}