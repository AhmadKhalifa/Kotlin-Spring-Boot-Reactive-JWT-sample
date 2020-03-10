package com.mongodb.reactive.reactivemongodb.service

import com.mongodb.reactive.reactivemongodb.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun findByUsername(username: String) = userRepository.findByUsername(username)
}