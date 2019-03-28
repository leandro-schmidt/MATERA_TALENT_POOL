package com.matera.restserver.exception;

public class EntityNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8032193805676955555L;

	public EntityNotFoundException(String msg) {
		super(msg);
	}

	public EntityNotFoundException() {
		super("The employee with given id was not found or is not active.");
	}

}
