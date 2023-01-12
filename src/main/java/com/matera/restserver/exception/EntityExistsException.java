package com.matera.restserver.exception;

import com.matera.restserver.util.Messages;

public class EntityExistsException extends Exception {

	private static final String DEFAULT_MESSAGE = "exceptions.default.entityexistsexception";

	private static final long serialVersionUID = -4802286605958048986L;

	public EntityExistsException() {
		super(Messages.getMessage(DEFAULT_MESSAGE));
	}

}
