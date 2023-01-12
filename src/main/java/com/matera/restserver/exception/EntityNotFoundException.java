package com.matera.restserver.exception;

import com.matera.restserver.util.Messages;

public class EntityNotFoundException extends Exception {

	private static final String DEFAULT_MESSAGE = "exceptions.default.entitynotfoundexception";

	private static final long serialVersionUID = 8032193805676955555L;

	public EntityNotFoundException(String msg) {
		super(msg);
	}

	public EntityNotFoundException() {
		super(Messages.getMessage(DEFAULT_MESSAGE));
	}

}
