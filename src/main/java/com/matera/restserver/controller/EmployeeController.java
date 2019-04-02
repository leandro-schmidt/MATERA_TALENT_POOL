package com.matera.restserver.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matera.restserver.dto.EmployeeDTO;
import com.matera.restserver.exception.EntityExistsException;
import com.matera.restserver.exception.EntityNotFoundException;
import com.matera.restserver.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	private static final String BAD_PARSING = "Something went wrong while parsing the body of the request :(";

	@Autowired
	private EmployeeService service;

	@Autowired
	private ObjectMapper mapper;

	private EmployeeDTO employee;

	/**
	 * Creates an employee.
	 * 
	 * @param reqBody A JSON that represents the employee being created, which will
	 *                come in the request body.
	 * @return the employee created
	 */
	@PostMapping(produces = "application/json", consumes = "application/json")
	public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody String reqBody, UriComponentsBuilder uri) {
		try {
			employee = mapper.readValue(reqBody, EmployeeDTO.class);
		} catch (IOException e1) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_PARSING, e1);
		}
		try {
			employee.setId(service.create(employee));
		} catch (EntityExistsException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		}

		// Return the location on the header
		UriComponents uriComponents = uri.path("/employee/{id}").buildAndExpand(employee.getId());

		return ResponseEntity.created(uriComponents.toUri()).body(employee);
	}

	/**
	 * Searchs all active employees.
	 * 
	 * @return All employees on the database.
	 */
	@GetMapping(produces = "application/json")
	public List<EmployeeDTO> findAllEmployees() {
		try {
			return service.findAll();
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	/**
	 * Searchs for an active employee with given id.
	 * 
	 * @param id id of the employee.
	 * @return employee with given id, if it exists.
	 */
	@GetMapping(value = "/{id}", produces = "application/json")
	public EmployeeDTO findEmployee(@PathVariable Long id) {
		try {
			return service.find(id);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	/**
	 * Deletes (deactivates) an employee with given id.
	 * 
	 * @param id
	 * @return the employee deactivated
	 */
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public EmployeeDTO deleteEmployee(@PathVariable Long id) {
		try {
			return service.delete(id);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);

		}

	}

	/**
	 * Updates an employee with given id.
	 * 
	 * @param id
	 * @return the employee updated
	 */
	@PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
	public EmployeeDTO updateEmployee(@PathVariable Long id, @RequestBody String reqBody) {
		try {
			employee = mapper.readValue(reqBody, EmployeeDTO.class);
		} catch (IOException e1) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_PARSING, e1);
		}

		// Ignore ID if informed on the request body
		employee.setId(id);

		try {
			service.update(employee);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}

		return employee;
	}

}