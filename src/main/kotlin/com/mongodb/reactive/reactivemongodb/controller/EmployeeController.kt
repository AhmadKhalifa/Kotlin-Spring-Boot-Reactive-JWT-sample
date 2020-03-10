package com.mongodb.reactive.reactivemongodb.controller

import com.mongodb.reactive.reactivemongodb.model.Employee
import com.mongodb.reactive.reactivemongodb.service.EmployeeService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/employee")
class EmployeeController(private val employeeService: EmployeeService) {

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    fun getAll() = employeeService.findAllEmployees()

    @PostMapping("/save")
    fun save(@RequestBody employee: Employee) = employeeService.saveEmployee(employee)

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") employeeId: String) = employeeService.findEmployeeById(employeeId)
}