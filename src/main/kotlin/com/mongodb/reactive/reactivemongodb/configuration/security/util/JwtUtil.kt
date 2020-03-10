package com.mongodb.reactive.reactivemongodb.configuration.security.util

import com.mongodb.reactive.reactivemongodb.model.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    @Value("\${jwt.expiration}")
    private lateinit var expirationTime: String

    fun getUsernameFromToken(token: String): String = getClaimFromToken(token, Claims::getSubject)

    fun getRolesFromToken(token: String): List<Role> = getAllClaimsFromToken(token).run {
        get(KEY_ROLE, List::class.java).map { value -> value as String }.map(Role::valueOf)
    }

    private fun getExpirationDateFromToken(token: String) = getClaimFromToken(token, Claims::getExpiration)

    private fun <T> getClaimFromToken(token: String, claimsResolver: (Claims) -> T): T =
            claimsResolver(getAllClaimsFromToken(token))

    private fun getAllClaimsFromToken(token: String): Claims = Jwts
            .parser()
            .setSigningKey(Base64.getEncoder().encodeToString(secret.toByteArray()))
            .parseClaimsJws(token)
            .body

    private fun isTokenExpired(token: String) = getExpirationDateFromToken(token).before(Date())

    fun validateToken(token: String, username: String) =
            !isTokenExpired(token) && getUsernameFromToken(token) == username

    fun generateToken(user: User): String = Jwts.builder()
            .setClaims(
                    Jwts.claims().setSubject(user.username).apply {
                        put(KEY_ROLE, user.roles)
                    }
            )
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expirationTime.toLong() * 1000))
            .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(secret.toByteArray()))
            .compact()
}