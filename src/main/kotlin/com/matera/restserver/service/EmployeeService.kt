package com.matera.restserver.service

import com.matera.restserver.dto.CreateEmployeeDTO
import com.matera.restserver.exception.EntityNotFoundException
import com.matera.restserver.model.Employee
import com.matera.restserver.repository.EmployeeRepository
import com.matera.restserver.util.Messages
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
open class EmployeeService(private val employeeRepository: EmployeeRepository) {

    fun create(dto: CreateEmployeeDTO): Long {
        return employeeRepository.save(
            Employee(
                firstName = dto.firstName,
                middleName = dto.middleName,
                lastName = dto.lastName,
                status = dto.status,
                dateOfEmployment = dto.dateOfEmployment,
                dateOfBirth = dto.dateOfBirth
            )
        ).id
    }

    fun find(id: Long): Employee {
        return employeeRepository.findByIdAndStatus(id, ACTIVE)
            ?: throw EntityNotFoundException("exceptions.default.entitynotfoundexception")
    }

    fun findAll(): List<Employee> {
        val employees = employeeRepository.findByStatus(ACTIVE)
        if (employees.isEmpty()) {
            throw EntityNotFoundException(Messages.getMessage("business.employee.employeesnotfound"))
        }
        return employees
    }

    fun delete(id: Long): Employee {
        val employee = employeeRepository.findByIdAndStatus(id, ACTIVE)
            ?: throw EntityNotFoundException("exceptions.default.entitynotfoundexception")
        employeeRepository.deleteById(id)
        return employee
    }

    fun update(dto: Employee) {
        val employee = employeeRepository.findByIdAndStatus(dto.id, ACTIVE)
            ?: throw EntityNotFoundException("exceptions.default.entitynotfoundexception")
        employeeRepository.save(
            Employee(
                id = dto.id,
                firstName = dto.firstName,
                middleName = dto.middleName,
                lastName = dto.lastName,
                status = dto.status,
                dateOfEmployment = dto.dateOfEmployment,
                dateOfBirth = dto.dateOfBirth
            )
        )
    }

    fun create(dto: List<CreateEmployeeDTO>?) {
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
        employeeRepository.saveAll(entities)
    }

    companion object {
        private const val ACTIVE = "ACTIVE"
    }
}
