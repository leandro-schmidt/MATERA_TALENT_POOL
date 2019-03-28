package com.matera.restserver.business;

import java.util.List;

import com.matera.restserver.dto.GenericDTO;
import com.matera.restserver.exception.EntityExistsException;
import com.matera.restserver.exception.EntityNotFoundException;

public interface GenericBusiness<T extends GenericDTO> {

	public Long create(T dto) throws EntityExistsException;

	public T find(Long id) throws EntityNotFoundException;

	public List<T> findAll() throws EntityNotFoundException;

	public T delete(Long id) throws EntityNotFoundException;

	public void update(T dto) throws EntityNotFoundException;

}
