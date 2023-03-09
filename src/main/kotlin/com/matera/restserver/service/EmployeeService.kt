package com.matera.restserver.service

import com.matera.restserver.dto.CreateEmployeeDTO
import com.matera.restserver.exception.EntityNotFoundException
import com.matera.restserver.model.Employee
import com.matera.restserver.repository.EmployeeRepository
import org.springframework.stereotype.Service
import java.util.UUID
import java.util.stream.Collectors

@Service
open class EmployeeService(private val employeeRepository: EmployeeRepository) {
    fun create(dto: CreateEmployeeDTO): Employee = employeeRepository.save(
        Employee(
            firstName = dto.firstName,
            middleName = dto.middleName,
            lastName = dto.lastName,
            status = dto.status,
            dateOfEmployment = dto.dateOfEmployment,
            dateOfBirth = dto.dateOfBirth
        )
    )

    fun find(id: UUID): Employee? {
        return employeeRepository.findByIdAndStatus(id, ACTIVE)
    }

    fun findAll(): List<Employee> = employeeRepository.findByStatus(ACTIVE)

    fun delete(id: UUID): Employee? {
        val employee = employeeRepository.findByIdAndStatus(id, ACTIVE)
        if (employee != null) employeeRepository.deleteById(id)
        return employee
    }

    fun update(dto: Employee): Employee {
        employeeRepository.findByIdAndStatus(dto.id, ACTIVE)
            ?: throw EntityNotFoundException("exceptions.default.entitynotfoundexception")
        val employee = Employee(
            id = dto.id,
            firstName = dto.firstName,
            middleName = dto.middleName,
            lastName = dto.lastName,
            status = dto.status,
            dateOfEmployment = dto.dateOfEmployment,
            dateOfBirth = dto.dateOfBirth
        )
        employeeRepository.save(
            employee
        )
        return employee
    }

    fun create(dto: List<CreateEmployeeDTO>?): List<Employee> {
        val entities = dto!!.stream().map { e: CreateEmployeeDTO ->
            val d = Employee(
                firstName = e.firstName,
                middleName = e.middleName,
                lastName = e.lastName,
                status = e.status,
                dateOfBirth = e.dateOfBirth,
                dateOfEmployment = e.dateOfEmployment
            )
            d
        }.collect(Collectors.toList())
        return employeeRepository.saveAll(entities)
    }

    companion object {
        private const val ACTIVE = "ACTIVE"
    }
}
