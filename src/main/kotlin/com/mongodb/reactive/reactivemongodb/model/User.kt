package com.mongodb.reactive.reactivemongodb.model

import com.mongodb.reactive.reactivemongodb.configuration.security.util.Role
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Document(collection = "users")
class User(
        @Indexed private var username: String,
        private var password: String,
        @Indexed var email: String
) : UserDetails {

    @Id
    var id: String? = null

    var roles: List<Role> = listOf()
    private var enabled: Boolean = true

    fun setPassword(password: String) {
        this.password = password
    }

    override fun getAuthorities() = roles.map { role -> SimpleGrantedAuthority(role.name) }

    override fun isEnabled() = enabled

    override fun getUsername() = username

    override fun isCredentialsNonExpired() = false

    override fun getPassword() = password

    override fun isAccountNonExpired() = false

    override fun isAccountNonLocked() = false
}