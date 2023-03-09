package com.matera.restserver.repository

import com.matera.restserver.model.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface EmployeeRepository : JpaRepository<Employee, UUID> {
    fun findByIdAndStatus(id: UUID, status: String): Employee?
    fun findByStatus(status: String): List<Employee>
}
