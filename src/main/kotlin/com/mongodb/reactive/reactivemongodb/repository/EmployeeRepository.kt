package com.mongodb.reactive.reactivemongodb.repository

import com.mongodb.reactive.reactivemongodb.model.Employee
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface EmployeeRepository : ReactiveMongoRepository<Employee, String>