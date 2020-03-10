package com.mongodb.reactive.reactivemongodb.configuration.security.jwt

import com.mongodb.reactive.reactivemongodb.configuration.security.util.JwtUtil
import com.mongodb.reactive.reactivemongodb.configuration.security.util.toSimpleGrantedAuthority
import com.mongodb.reactive.reactivemongodb.util.error
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AuthenticationManager(private val jwtUtil: JwtUtil) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication?): Mono<Authentication> {
        val token = authentication?.credentials.toString()
        var username: String? = null
        try {
            username = jwtUtil.getUsernameFromToken(token)
        } catch (exception: Exception) {
            error("Error authenticating token '${token}'", exception)
        }
        return if (username != null && jwtUtil.validateToken(token, username)) {
            Mono.just(UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    jwtUtil.getRolesFromToken(token).toSimpleGrantedAuthority()
            ))
        } else {
            Mono.empty()
        }
    }
}