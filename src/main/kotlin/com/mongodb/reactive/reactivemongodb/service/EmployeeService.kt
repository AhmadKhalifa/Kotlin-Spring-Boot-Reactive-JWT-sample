package com.mongodb.reactive.reactivemongodb.service

import com.mongodb.reactive.reactivemongodb.model.Employee
import com.mongodb.reactive.reactivemongodb.repository.EmployeeRepository
import org.springframework.stereotype.Service

@Service
class EmployeeService(private val employeeRepository: EmployeeRepository) {

    fun findAllEmployees() = employeeRepository.findAll()

    fun findEmployeeById(employeeId: String) = employeeRepository.findById(employeeId)

    fun saveEmployee(employee: Employee) = employeeRepository.save(employee)
}