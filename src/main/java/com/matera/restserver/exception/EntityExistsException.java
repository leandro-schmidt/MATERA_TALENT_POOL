package com.matera.restserver.exception;

public class EntityExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4802286605958048986L;

	public EntityExistsException() {
		super("There is already an employee with the given Id. For update, please use http PUT method and URI parameter.");
	}

}
