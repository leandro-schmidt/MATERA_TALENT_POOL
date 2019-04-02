package com.matera.restserver.service;

import java.util.List;

import com.matera.restserver.dto.EmployeeDTO;
import com.matera.restserver.exception.EntityExistsException;
import com.matera.restserver.exception.EntityNotFoundException;

public interface EmployeeService {

	Long create(EmployeeDTO dto) throws EntityExistsException;
	
	void create(List<EmployeeDTO> dto);

	EmployeeDTO find(Long id) throws EntityNotFoundException;

	List<EmployeeDTO> findAll() throws EntityNotFoundException;

	EmployeeDTO delete(Long id) throws EntityNotFoundException;

	void update(EmployeeDTO dto) throws EntityNotFoundException;

}
