package com.mongodb.reactive.reactivemongodb.service

import com.mongodb.reactive.reactivemongodb.model.Employee
import com.mongodb.reactive.reactivemongodb.repository.EmployeeReactiveRepository
import com.mongodb.reactive.reactivemongodb.repository.EmployeeRepository
import org.springframework.stereotype.Service

@Service
class EmployeeService(
        private val employeeReactiveRepository: EmployeeReactiveRepository,
        private val employeeRepository: EmployeeRepository
) {

    fun findAllEmployees() = employeeReactiveRepository.findAll()

    fun findEmployeeById(employeeId: String) = employeeRepository.byId(employeeId)

    fun saveEmployee(employee: Employee) = employeeReactiveRepository.save(employee)
}