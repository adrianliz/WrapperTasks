package domain.enums;

public enum WindowIndicator {
	MUSIC_MAIN_WINDOW("Multi-User System for Interactive Computing"),
	MUSIC_LOGIN_WINDOW("sign on"),
	MUSIC_USERID_UNAUTHORIZED("Userid is not authorized"),
	MUSIC_PWD_INCORRECT("Password incorrect"),
	MUSIC_USERID_IN_USE("Userid in use"),
	MUSIC_COMMAND_LINE("Full Screen Interface for MUSIC"),
	TASKS2_MAIN_WINDOW("TASK MANAGEMENT 2.0 BY TURO-SL SOFT");

	private final String indicator;

	WindowIndicator(String indicator) {
		this.indicator = indicator;
	}

	@Override
	public String toString() {
		return indicator;
	}
}
