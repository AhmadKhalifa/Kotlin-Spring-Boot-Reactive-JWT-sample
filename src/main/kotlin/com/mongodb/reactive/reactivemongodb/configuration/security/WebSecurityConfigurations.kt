package com.mongodb.reactive.reactivemongodb.configuration.security

import com.mongodb.reactive.reactivemongodb.configuration.security.jwt.AuthenticationManager
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class WebSecurityConfigurations(
        private val authenticationManager: AuthenticationManager,
        private val securityContextRepository: ServerSecurityContextRepository
) {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain = http
            .exceptionHandling()
            .authenticationEntryPoint { serverWebExchange: ServerWebExchange, _: AuthenticationException? ->
                Mono.fromRunnable { serverWebExchange.response.statusCode = HttpStatus.UNAUTHORIZED }
            }
            .accessDeniedHandler { serverWebExchange: ServerWebExchange, _: AccessDeniedException? ->
                Mono.fromRunnable { serverWebExchange.response.statusCode = HttpStatus.FORBIDDEN }
            }
            .and()
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .authenticationManager(authenticationManager)
            .securityContextRepository(securityContextRepository)
            .authorizeExchange()
            .pathMatchers(HttpMethod.OPTIONS).permitAll()
            .pathMatchers("/login", "/register").permitAll()
            .anyExchange().authenticated()
            .and().build()
}