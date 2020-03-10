package com.mongodb.reactive.reactivemongodb.payload

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

open class Response<T> protected constructor(body: Resource<T>) : ResponseEntity<Resource<T>>(body, body.status) {

    companion object {

        fun <T> success(data: T?) =
                Response(Resource(data = data))

        fun <T> error(status: HttpStatus, message: String = "") =
                Response(Resource<T>(data = null, status = status, message = message))
    }

    fun isSuccessful() = statusCode.is2xxSuccessful
}