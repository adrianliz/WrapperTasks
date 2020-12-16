package domain.exceptions;

import domain.enums.WindowIndicator;

public class InvalidScreenException extends Exception {
	public InvalidScreenException(WindowIndicator indicator) {
		super("Can't found indicator: '" + indicator + "' in the screen");
	}
}
