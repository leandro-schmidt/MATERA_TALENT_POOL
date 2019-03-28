package com.matera.restserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matera.restserver.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	List<Employee> findByIdAndStatus(Long id, String status);

	List<Employee> findByStatus(String status);

}
