package com.matera.restserver.business;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.beans.BeanUtils;

import com.matera.restserver.dto.EmployeeDTO;
import com.matera.restserver.exception.EntityExistsException;
import com.matera.restserver.exception.EntityNotFoundException;
import com.matera.restserver.model.Employee;
import com.matera.restserver.repository.EmployeeRepository;
import com.matera.restserver.util.Messages;

@Component
public class EmployeeBusiness implements GenericBusiness<EmployeeDTO> {

	private static final String INACTIVE = "INACTIVE";

	private static final String ACTIVE = "ACTIVE";

	@Autowired
	private EmployeeRepository rep;

	/**
	 * The JPA Employee entity
	 */
	Employee entity;

	/**
	 * The Employee DTO 
	 */
	EmployeeDTO dto;

	/**
	 * List of JPA Employee entity
	 */
	List<Employee> entities;
	
	/**
	 * Creates an Employee
	 */
	public Long create(EmployeeDTO dto) throws EntityExistsException {
		/**
		 * If you are trying to be sneaky (or you made a mistake, who knows?) and try to
		 * run an update instead of an insert (even with the documentation saying that
		 * you must not inform an id for this operation)
		 */
		if (rep.findByIdAndStatus(dto.getId(), ACTIVE).isEmpty()) {
			entity = new Employee();
			BeanUtils.copyProperties(dto, entity, "id");
			entity.setStatus(ACTIVE);
			rep.save(entity);
			return entity.getId();
		} else {
			throw new EntityExistsException();
		}
	}
	
	/**
	 * Finds an Employee with given id
	 */
	public EmployeeDTO find(Long id) throws EntityNotFoundException {
		dto = new EmployeeDTO();
		entities = rep.findByIdAndStatus(id, ACTIVE);

		if (entities.isEmpty()) {
			throw new EntityNotFoundException();
		}

		entity = entities.get(0);
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	/**
	 * Finds all Employees
	 */
	public List<EmployeeDTO> findAll() throws EntityNotFoundException {
		List<EmployeeDTO> dtos;
		entities = rep.findByStatus(ACTIVE);

		/*
		 * Using a stream to create all the DTOs based on the entities Java 8 rocks \,,/
		 * :)
		 * 
		 */
		dtos = entities.stream().map(e -> {
			EmployeeDTO d = new EmployeeDTO();
			BeanUtils.copyProperties(e, d);
			return d;
		}).collect(Collectors.toList());

		if (dtos.isEmpty()) {
			throw new EntityNotFoundException(Messages.getMessage("business.employee.employeesnotfound"));
		}

		return dtos;
	}

	/**
	 * Deletes an Employee
	 */
	public EmployeeDTO delete(Long id) throws EntityNotFoundException {
		entities = rep.findByIdAndStatus(id, ACTIVE);
		dto = new EmployeeDTO();
		
		if (entities.isEmpty()) {
			throw new EntityNotFoundException();
		}

		entity = entities.get(0);
		entity.setStatus(INACTIVE);
		rep.save(entity);
		BeanUtils.copyProperties(entity, dto);
		return dto;

	}

	/**
	 * Updates an Employee
	 */
	public void update(EmployeeDTO dto) throws EntityNotFoundException {
		entities = rep.findByIdAndStatus(dto.getId(), ACTIVE);

		if (entities.isEmpty()) {
			throw new EntityNotFoundException();
		}

		entity = entities.get(0);
		BeanUtils.copyProperties(dto, entity, "id");
		rep.save(entity);
	}

	
	/**
	 * Creates a bunch of Employees
	 */
	public void create(List<EmployeeDTO> dto) {
		
		entities = dto.stream().map(e -> {
			Employee d = new Employee();
			BeanUtils.copyProperties(e, d);
			return d;
		}).collect(Collectors.toList());
		
		rep.saveAll(entities);
	}

}
