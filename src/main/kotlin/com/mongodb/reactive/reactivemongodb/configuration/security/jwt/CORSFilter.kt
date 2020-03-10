package com.mongodb.reactive.reactivemongodb.configuration.security.jwt

import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer

class CORSFilter : WebFluxConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        super.addCorsMappings(registry)
    }
}