package com.mongodb.reactive.reactivemongodb.controller

import com.mongodb.reactive.reactivemongodb.model.Employee
import com.mongodb.reactive.reactivemongodb.service.EmployeeService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/employee")
class EmployeeController(private val employeeService: EmployeeService) {

    @GetMapping("/all")
    fun getAll(): Flux<Employee> {
        return employeeService.findAllEmployees()
    }

    @PostMapping("/save")
    fun save(@RequestBody employee: Employee): Mono<Employee> {
        return employeeService.saveEmployee(employee)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") employeeId: String): Employee {
        return employeeService.findEmployeeById(employeeId)
    }
}