package domain.enums;

public enum ErrorMessage {
	USERID_UNAUTHORIZED("Error: UserID is unauthorized"),
	PWD_INCORRECT("Error: Password incorrect"),
	USERID_IN_USE("Error: UserID is in use"),
	JOB_NOT_RUNNING("Error: Job isn't running"),
	JOB_NOT_FINISHED("Error: Can't finish job"),
	INVALID_SCREEN("Error: Invalid screen state reached"),
	IO("Error: An IO error occurred");

	private final String message;

	ErrorMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}
}
