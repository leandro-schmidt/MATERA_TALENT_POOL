package com.matera.restserver.controller

import com.matera.restserver.dto.CreateEmployeeDTO
import com.matera.restserver.exception.EntityNotFoundException
import com.matera.restserver.model.Employee
import com.matera.restserver.service.EmployeeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.util.UriComponentsBuilder
import java.util.UUID

@RestController
@RequestMapping("/employee")
open class EmployeeController(private val employeeService: EmployeeService) {

    @PostMapping(produces = ["application/json"], consumes = ["application/json"])
    fun createEmployee(
        @RequestBody employee: CreateEmployeeDTO,
        uri: UriComponentsBuilder
    ): ResponseEntity<Employee> {
        val employeeCreated = employeeService.create(employee)
        return ResponseEntity.created(
            uri.path("/employee/{id}")
                .buildAndExpand(employeeCreated.id).toUri()
        ).body(employeeCreated)
    }

    @PutMapping(value = ["/{id}"], produces = ["application/json"], consumes = ["application/json"])
    fun updateEmployee(@PathVariable id: UUID, @RequestBody employee: Employee): Employee? =
        try {
            employee.id = id
            employeeService.update(employee)
        } catch (e: EntityNotFoundException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }

    @GetMapping(produces = ["application/json"])
    fun findAllEmployees(): List<Employee> = employeeService.findAll()

    @GetMapping(value = ["/{id}"], produces = ["application/json"])
    fun findEmployee(@PathVariable id: UUID): Employee? = employeeService.find(id)

    @DeleteMapping(value = ["/{id}"], produces = ["application/json"])
    fun deleteEmployee(@PathVariable id: UUID): Employee? = employeeService.delete(id)
}
