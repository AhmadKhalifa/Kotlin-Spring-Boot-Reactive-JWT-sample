package com.mongodb.reactive.reactivemongodb.configuration.security.util

import org.springframework.security.core.authority.SimpleGrantedAuthority

const val KEY_ROLE = "role"

enum class Role {
    ROLE_USER,
    ROLE_ADMIN
}

fun List<Role>.toSimpleGrantedAuthority() = map { role -> SimpleGrantedAuthority(role.name) }

