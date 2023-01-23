package com.matera.restserver.controller

import com.matera.restserver.dto.CreateEmployeeDTO
import com.matera.restserver.exception.EntityExistsException
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

@RestController
@RequestMapping("/employee")
open class EmployeeController(private val employeeService: EmployeeService) {

    @PostMapping(produces = ["application/json"], consumes = ["application/json"])
    fun createEmployee(
        @RequestBody employee: CreateEmployeeDTO,
        uri: UriComponentsBuilder
    ): ResponseEntity<CreateEmployeeDTO?> {
        var id: Long
        try {
            id = employeeService.create(employee)
        } catch (e: EntityExistsException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }

        // Return the location on the header
        val uriComponents = uri.path("/employee/{id}").buildAndExpand(id)
        return ResponseEntity.created(uriComponents.toUri()).body(employee)
    }

    @GetMapping(produces = ["application/json"])
    fun findAllEmployees(): List<Employee> {
        return try {
            employeeService.findAll()
        } catch (e: EntityNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @GetMapping(value = ["/{id}"], produces = ["application/json"])
    fun findEmployee(@PathVariable id: Long): Employee {
        return try {
            employeeService.find(id)
        } catch (e: EntityNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @DeleteMapping(value = ["/{id}"], produces = ["application/json"])
    fun deleteEmployee(@PathVariable id: Long): Employee {
        return try {
            employeeService.delete(id)
        } catch (e: EntityNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @PutMapping(value = ["/{id}"], produces = ["application/json"], consumes = ["application/json"])
    fun updateEmployee(@PathVariable id: Long, @RequestBody employee: Employee): Employee? {
        employee.id = id
        try {
            employeeService.update(employee)
        } catch (e: EntityNotFoundException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }
        return employee
    }
}
