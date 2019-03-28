package com.matera.restserver.service;

import java.util.List;

import com.matera.restserver.dto.EmployeeDTO;
import com.matera.restserver.exception.EntityExistsException;
import com.matera.restserver.exception.EntityNotFoundException;

public interface EmployeeService {

	public Long create(EmployeeDTO dto) throws EntityExistsException;

	public EmployeeDTO find(Long id) throws EntityNotFoundException;

	public List<EmployeeDTO> findAll() throws EntityNotFoundException;

	public EmployeeDTO delete(Long id) throws EntityNotFoundException;

	public void update(EmployeeDTO dto) throws EntityNotFoundException;

}
