@file:Suppress("BlockingMethodInNonBlockingContext")

package com.mongodb.reactive.reactivemongodb.service

import com.mongodb.reactive.reactivemongodb.configuration.security.util.JwtUtil
import com.mongodb.reactive.reactivemongodb.configuration.security.util.PBKDF2Encoder
import com.mongodb.reactive.reactivemongodb.configuration.security.util.Role
import com.mongodb.reactive.reactivemongodb.model.User
import com.mongodb.reactive.reactivemongodb.payload.Credentials
import com.mongodb.reactive.reactivemongodb.payload.Response
import com.mongodb.reactive.reactivemongodb.payload.Token
import com.mongodb.reactive.reactivemongodb.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AuthenticationService(
        private val jwtUtil: JwtUtil,
        private val passwordEncoder: PBKDF2Encoder,
        private val userRepository: UserRepository
) {

    fun login(credentials: Credentials): Mono<Response<Token>> = userRepository.findByUsername(credentials.username)
            .map { savedUser ->
                if (passwordEncoder.encode(credentials.password) == savedUser.password) {
                    val token = jwtUtil.generateToken(savedUser)
                    Response.success(Token(token))
                } else {
                    Response.error(status = HttpStatus.BAD_REQUEST, message = "Incorrect password")
                }
            }.defaultIfEmpty(Response.error(
                    status = HttpStatus.NOT_FOUND,
                    message = "No user with username '${credentials.username}'"
            ))

    fun register(user: User): Mono<Response<User>> = userRepository.findByEmail(user.email)
            .flatMap {
                Mono.just(Response.error<User>(
                        HttpStatus.BAD_REQUEST,
                        "Email '${user.email}' already exists"
                ))
            }
            .defaultIfEmpty(Response.success(user))
            .flatMap { emailValidationResponse ->
                if (emailValidationResponse.isSuccessful()) {
                    userRepository.findByUsername(user.username)
                            .flatMap {
                                Mono.just(Response.error<User>(
                                        HttpStatus.BAD_REQUEST,
                                        "Username '${user.username}' already exists"
                                ))
                            }
                            .defaultIfEmpty(emailValidationResponse)
                            .flatMap { usernameValidationResponse ->
                                if (usernameValidationResponse.isSuccessful()) {
                                    user.apply {
                                        roles = listOf(Role.ROLE_USER)
                                        password = passwordEncoder.encode(password)
                                    }
                                    userRepository.save(user).flatMap { savedUser ->
                                        Mono.just(Response.success(savedUser))
                                    }
                                } else {
                                    Mono.just(usernameValidationResponse)
                                }
                            }
                } else {
                    Mono.just(emailValidationResponse)
                }
            }
}