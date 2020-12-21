package domain.exceptions;

import domain.enums.ErrorMessage;

public class AuthException extends Exception {
	public AuthException(ErrorMessage message) {
		super(message.toString());
	}
}
