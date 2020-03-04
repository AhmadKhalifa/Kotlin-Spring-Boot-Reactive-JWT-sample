package com.mongodb.reactive.reactivemongodb.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Employee {
    @Id
    var id: String? = null
    var name: String? = null
    var salary: Long = -1
}