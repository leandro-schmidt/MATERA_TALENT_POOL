package com.matera.restserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.matera.restserver.business.EmployeeBusiness;
import com.matera.restserver.dto.EmployeeDTO;
import com.matera.restserver.exception.EntityExistsException;
import com.matera.restserver.exception.EntityNotFoundException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeBusiness business;

	@Transactional
	public Long create(EmployeeDTO dto) throws EntityExistsException {
		return business.create(dto);
	}

	@Transactional(readOnly = true)
	public EmployeeDTO find(Long id) throws EntityNotFoundException {
		return business.find(id);
	}

	@Transactional(readOnly = true)
	public List<EmployeeDTO> findAll() throws EntityNotFoundException {
		return business.findAll();
	}

	@Transactional
	public EmployeeDTO delete(Long id) throws EntityNotFoundException {
		return business.delete(id);
	}

	@Transactional
	public void update(EmployeeDTO dto) throws EntityNotFoundException {
		business.update(dto);
	}

	@Transactional
	public void create(List<EmployeeDTO> dto) {
		business.create(dto);
	}

}
