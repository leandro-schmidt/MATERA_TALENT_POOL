package com.matera.restserver.repository

import com.matera.restserver.model.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<Employee, Long> {
    fun findByIdAndStatus(id: Long, status: String): List<Employee>
    fun findByStatus(status: String): List<Employee>
}
