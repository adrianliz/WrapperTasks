package application;

import domain.MainframeAPI;
import domain.Proxy3270Emulator;
import domain.Response3270;

import java.io.IOException;

public class MusicAPI implements MainframeAPI {
	private static final long DEFAULT_TIMEOUT = 5; //s

	private static final String OFF = "off";

	private static final String LOGIN_WINDOW_INDICATOR = "sign on";
	private static final String POST_LOGIN_WINDOW_INDICATOR = "Press ENTER to continue...";
	private static final String COMMAND_LINE_WINDOW_INDICATOR =
		"--------------------Full Screen Interface for MUSIC------------------";
	static final String MAIN_WINDOW_INDICATOR =
		"Multi-User System for Interactive Computing";

	private final Proxy3270Emulator emulator;

	public MusicAPI(Proxy3270Emulator emulator) {
		this.emulator = emulator;
	}

	@Override
	public boolean login(String user, String pwd) throws IOException, InterruptedException {
		return ((emulator.enter().success()) &&
						(emulator.waitScreen(LOGIN_WINDOW_INDICATOR, DEFAULT_TIMEOUT)) &&
			  	  (emulator.syncWrite(user).success()) &&
						(emulator.enter().success()) &&
						(emulator.syncWrite(pwd).success()) &&
						(emulator.enter().success()) &&
					  (emulator.waitScreen(POST_LOGIN_WINDOW_INDICATOR, DEFAULT_TIMEOUT)) &&
						(emulator.enter().success()) &&
						(emulator.waitScreen(COMMAND_LINE_WINDOW_INDICATOR, DEFAULT_TIMEOUT)));
	}

	@Override
	public boolean logout() throws IOException, InterruptedException {
		return ((emulator.syncWrite(OFF).success()) &&
						(emulator.enter().success()) &&
						(emulator.waitScreen(MAIN_WINDOW_INDICATOR, DEFAULT_TIMEOUT)));
	}

	@Override
	public boolean executeJob(String jobName) {
		return false;
	}
}
