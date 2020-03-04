package com.mongodb.reactive.reactivemongodb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactiveMongodbApplication

fun main(args: Array<String>) {
    runApplication<ReactiveMongodbApplication>(*args)
}
