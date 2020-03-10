package com.mongodb.reactive.reactivemongodb.payload

import org.springframework.http.HttpStatus

data class Resource<T>(val data: T? = null, val status: HttpStatus = HttpStatus.OK, val message: String = "") {

    @Suppress("unused")
    val statusCode: Int
        get() = status.value()
}