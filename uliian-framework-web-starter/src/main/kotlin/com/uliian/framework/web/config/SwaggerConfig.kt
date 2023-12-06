package com.uliian.framework.web.config


import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*


//@EnableOpenApi
@Configuration
@ConditionalOnMissingBean(OpenAPI::class)
class SwaggerConfig {
    @Value("\${spring.application.name}")
    private val applicationName: String? = null

    @Bean
    fun springShopOpenAPI(): OpenAPI? {
        return OpenAPI()
            .components(
                Components().addSecuritySchemes(
                    "bearer-jwt",
                    SecurityScheme().type(SecurityScheme.Type.APIKEY)
                        .`in`(SecurityScheme.In.HEADER).name("Authorization")
                ))
            .info(
                Info().title("$applicationName Api Doc")
            ) .addSecurityItem(
                SecurityRequirement().addList("bearer-jwt", Arrays.asList("read", "write"))
            )
    }
}