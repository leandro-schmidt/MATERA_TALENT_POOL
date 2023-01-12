package com.matera.restserver.business

import com.matera.restserver.dto.EmployeeDTO
import com.matera.restserver.exception.EntityExistsException
import com.matera.restserver.exception.EntityNotFoundException
import com.matera.restserver.model.Employee
import com.matera.restserver.repository.EmployeeRepository
import com.matera.restserver.util.Messages
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class EmployeeBusiness(private val rep: EmployeeRepository) {
    private var entity: Employee? = null
    private var dto: EmployeeDTO? = null
    private var entities: List<Employee>? = null

    fun create(dto: EmployeeDTO): Long? {
        /**
         * If you are trying to be sneaky (or you made a mistake, who knows?) and try to
         * run an update instead of an insert (even with the documentation saying that
         * you must not inform an id for this operation)
         */
        return if (rep.findByIdAndStatus(dto.id, ACTIVE).isEmpty()) {
            entity = Employee(
                id = null,
                firstName = dto.firstName,
                middleName = dto.middleName,
                lastName = dto.lastName,
                status = dto.status,
                dateOfBirth = dto.dateOfBirth,
                dateOfEmployment = dto.dateOfEmployment
            )
            entity!!.status = ACTIVE
            rep.save(entity)
            entity!!.id
        } else {
            throw EntityExistsException()
        }
    }

    fun find(id: Long?): EmployeeDTO {
        entities = rep.findByIdAndStatus(id, ACTIVE)
        if (entities!!.isEmpty()) {
            throw EntityNotFoundException()
        }
        entity = entities!![0]
        dto = EmployeeDTO(
            entity!!.id,
            entity!!.firstName,
            entity!!.middleName,
            entity!!.lastName,
            entity!!.status,
            entity!!.dateOfBirth,
            entity!!.dateOfEmployment
        )
        return dto!!
    }

    fun findAll(): List<EmployeeDTO>? {
        entities = rep.findByStatus(ACTIVE)
        val dtos: List<EmployeeDTO> = entities!!.stream().map { e: Employee? ->
            val d = EmployeeDTO(
                id = e!!.id,
                firstName = e.firstName,
                middleName = e.middleName,
                lastName = e.lastName,
                status = e.status,
                dateOfBirth = e.dateOfBirth,
                dateOfEmployment = e.dateOfEmployment
                )
            d
        }.collect(Collectors.toList())
        if (dtos.isEmpty()) {
            throw EntityNotFoundException(Messages.getMessage("business.employee.employeesnotfound"))
        }
        return dtos
    }

    fun delete(id: Long?): EmployeeDTO {
        entities = rep.findByIdAndStatus(id, ACTIVE)
        if (entities!!.isEmpty()) {
            throw EntityNotFoundException()
        }
        entity = entities!![0]
        entity!!.status = INACTIVE
        rep.save(entity)
        dto  = EmployeeDTO(
            id = entity!!.id,
            firstName = entity!!.firstName,
            middleName = entity!!.middleName,
            lastName = entity!!.lastName,
            status = entity!!.status,
            dateOfBirth = entity!!.dateOfBirth,
            dateOfEmployment = entity!!.dateOfEmployment
        )
        return dto!!
    }

    fun update(dto: EmployeeDTO) {
        entities = rep.findByIdAndStatus(dto.id, ACTIVE)
        if (entities!!.isEmpty()) {
            throw EntityNotFoundException()
        }
        entity = entities!![0]
        entity = Employee(
            id = entity!!.id,
            firstName = dto.firstName,
            middleName = dto.middleName,
            lastName = dto.lastName,
            status = dto.status,
            dateOfBirth = dto.dateOfBirth,
            dateOfEmployment = dto.dateOfEmployment
        )
        rep.save(entity)
    }

    fun create(dto: List<EmployeeDTO>?) {
        entities = dto!!.stream().map { e: EmployeeDTO? ->
            val d = Employee(
                id = e!!.id,
                firstName = e.firstName,
                middleName = e.middleName,
                lastName = e.lastName,
                status = e.status,
                dateOfBirth = e.dateOfBirth,
                dateOfEmployment = e.dateOfEmployment
            )
            d
        }.collect(Collectors.toList())
        rep.saveAll(entities)
    }

    companion object {
        private const val INACTIVE = "INACTIVE"
        private const val ACTIVE = "ACTIVE"
    }
}