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
	/*
	 * Created a service and a business layer separated because sometimes, a given
	 * service may have to deal with the business logic of more than one entity.
	 * Another possibility would be create just the service layer and handle all the
	 * business here, and if some service in the future needs to deal with more
	 * entities, create a Facade object that deal with both respective services.
	 * BUT, doing it the way I did, I think it's better because it's already more
	 * explicit that there may be 'entity business logic' and 'interaction of
	 * entities business logic'; the first case must be done in the business layer,
	 * and the second here. Plus here we 'manage' the transaction, and business
	 * layer don't deal with it.
	 */

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
