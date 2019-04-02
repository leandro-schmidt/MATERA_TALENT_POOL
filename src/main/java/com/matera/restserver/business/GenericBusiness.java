package com.matera.restserver.business;

import java.util.List;

import com.matera.restserver.dto.GenericDTO;
import com.matera.restserver.exception.EntityExistsException;
import com.matera.restserver.exception.EntityNotFoundException;

public interface GenericBusiness<T extends GenericDTO> {

	Long create(T dto) throws EntityExistsException;

	void create(List<T> dto);
	
	T find(Long id) throws EntityNotFoundException;

	List<T> findAll() throws EntityNotFoundException;

	T delete(Long id) throws EntityNotFoundException;

	void update(T dto) throws EntityNotFoundException;

}
