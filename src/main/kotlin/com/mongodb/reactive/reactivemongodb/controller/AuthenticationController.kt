package com.mongodb.reactive.reactivemongodb.controller

import com.mongodb.reactive.reactivemongodb.model.User
import com.mongodb.reactive.reactivemongodb.payload.Credentials
import com.mongodb.reactive.reactivemongodb.service.AuthenticationService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationController(private val authenticationService: AuthenticationService) {

    @PostMapping("/login")
    fun login(@RequestBody credentials: Credentials) = authenticationService.login(credentials)

    @PostMapping("/register")
    fun register(@RequestBody user: User) = authenticationService.register(user)

    @PostMapping("/create-user")
    @PreAuthorize("hasRole('ADMIN')")
    fun createUser(@RequestBody user: User) = authenticationService.register(user)
}