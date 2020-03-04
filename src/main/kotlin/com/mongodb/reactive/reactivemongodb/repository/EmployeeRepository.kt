package com.mongodb.reactive.reactivemongodb.repository

import com.mongodb.reactive.reactivemongodb.model.Employee
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface EmployeeRepository : MongoRepository<Employee, String> {

    @Query("{ id: ?0 }")
    fun byId(employeeId: String): Employee
}