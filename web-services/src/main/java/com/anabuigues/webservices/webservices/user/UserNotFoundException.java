package com.anabuigues.webservices.webservices.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -543572396585089509L;

	public UserNotFoundException(String message) {
		super(message);
	}

}
