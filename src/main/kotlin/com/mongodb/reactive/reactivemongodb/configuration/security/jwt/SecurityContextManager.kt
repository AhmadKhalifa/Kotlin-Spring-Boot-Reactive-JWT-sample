package com.mongodb.reactive.reactivemongodb.configuration.security.jwt

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

private const val AUTH_HEADER_PREFIX = "Bearer "

@Component
class SecurityContextManager(private val authenticationManager: AuthenticationManager)
    : ServerSecurityContextRepository {

    override fun save(exchange: ServerWebExchange?, context: SecurityContext?): Mono<Void> {
        throw UnsupportedOperationException("Not supported")
    }

    override fun load(exchange: ServerWebExchange?): Mono<SecurityContext> = if (exchange != null &&
            exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)?.startsWith(AUTH_HEADER_PREFIX) == true) {
        val token = (exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION))?.substring(AUTH_HEADER_PREFIX.length)
        val authentication = UsernamePasswordAuthenticationToken(token, token)
        authenticationManager.authenticate(authentication).map { SecurityContextImpl(it) }
    } else Mono.empty()
}