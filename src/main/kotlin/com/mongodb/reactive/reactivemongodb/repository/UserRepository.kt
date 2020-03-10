package com.mongodb.reactive.reactivemongodb.repository

import com.mongodb.reactive.reactivemongodb.model.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface UserRepository : ReactiveMongoRepository<User, Long> {

    fun findByUsername(username: String): Mono<User>

    fun findByEmail(email: String): Mono<User>

    fun findByUsernameOrEmail(username: String, email: String): Mono<User>
}