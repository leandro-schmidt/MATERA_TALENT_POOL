package com.matera.restserver.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.matera.restserver.dto.EmployeeDTO
import com.matera.restserver.exception.EntityExistsException
import com.matera.restserver.exception.EntityNotFoundException
import com.matera.restserver.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException

@RestController
@RequestMapping("/employee")
class EmployeeController {
    @Autowired
    private val service: EmployeeService? = null

    @PostMapping(produces = ["application/json"], consumes = ["application/json"])
    fun createEmployee(@RequestBody employee: EmployeeDTO?, uri: UriComponentsBuilder): ResponseEntity<EmployeeDTO?> {
        try {
            employee?.id = service!!.create(employee)
        } catch (e: EntityExistsException) {
            throw ResponseStatusException(HttpStatus.CONFLICT, e.message, e)
        }

        // Return the location on the header
        val uriComponents = uri.path("/employee/{id}").buildAndExpand(employee!!.id)
        return ResponseEntity.created(uriComponents.toUri()).body(employee)
    }

    @GetMapping(produces = ["application/json"])
    fun findAllEmployees(): List<EmployeeDTO> {
        return try {
            service!!.findAll()
        } catch (e: EntityNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @GetMapping(value = ["/{id}"], produces = ["application/json"])
    fun findEmployee(@PathVariable id: Long?): EmployeeDTO {
        return try {
            service!!.find(id)
        } catch (e: EntityNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @DeleteMapping(value = ["/{id}"], produces = ["application/json"])
    fun deleteEmployee(@PathVariable id: Long?): EmployeeDTO {
        return try {
            service!!.delete(id)
        } catch (e: EntityNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @PutMapping(value = ["/{id}"], produces = ["application/json"], consumes = ["application/json"])
    fun updateEmployee(@PathVariable id: Long?, @RequestBody employee: EmployeeDTO?): EmployeeDTO? {
        // Ignore ID if informed on the request body
        employee?.id = id
        try {
            service!!.update(employee)
        } catch (e: EntityNotFoundException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }
        return employee
    }

    companion object {
        private const val BAD_PARSING = "Something went wrong while parsing the body of the request :("
    }
}