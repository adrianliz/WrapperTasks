package domain.enums;

public enum ScreenIndicator {
	MUSIC_MAIN_WINDOW("Multi-User System for Interactive Computing"),
	MUSIC_LOGIN_WINDOW("sign on"),
	MUSIC_USERID_UNAUTHORIZED("Userid is not authorized"),
	MUSIC_PWD_INCORRECT("Password incorrect"),
	MUSIC_USERID_IN_USE("Userid in use"),
	MUSIC_COMMAND_LINE("Full Screen Interface for MUSIC"),
	TASKS2_MAIN_WINDOW("TASK MANAGEMENT 2.0 BY TURO-SL SOFT"),
	TASKS2_NEW_TASK_FILE_WINDOW("**NEW TASK FILE**"),
	TASKS2_NEW_TASK_FILE_CREATED("NEW TASK FILE HAS BEEN CREATED"),
	TASKS2_BYE_WINDOW("**EXIT**");

	private final String indicator;

	ScreenIndicator(String indicator) {
		this.indicator = indicator;
	}

	@Override
	public String toString() {
		return indicator;
	}
}
