package application;

import domain.MainframeAPI;
import domain.Proxy3270Emulator;
import domain.enums.WindowIndicator;
import domain.exceptions.InvalidScreenException;
import domain.exceptions.AuthException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MusicAPI implements MainframeAPI {
	private static final Map<String, WindowIndicator> appIndicator = new HashMap<>();
	static {appIndicator.put("tasks2.job", WindowIndicator.TASKS2_MAIN_WINDOW);}

	private static final long DEFAULT_TIMEOUT = 5; //s
	private static final String OFF = "off";
	private static final String INVALID_SCREEN_ERROR = "Invalid screen state reached";
	private static final String IO_ERROR = "An IO error succeeded";

	private final Proxy3270Emulator emulator;

	public MusicAPI(Proxy3270Emulator emulator) {
		this.emulator = emulator;
	}

	private void writeLoginField(String field, WindowIndicator errorIndicator)
		throws IOException, AuthException {

		emulator.syncWrite(field);
		emulator.enter();

		if (emulator.syncBufferRead(DEFAULT_TIMEOUT).contains(errorIndicator.toString())) {
			emulator.clearFields();
			throw new AuthException(errorIndicator.toString());
		}
	}

	// probar si cuando se hace throw se continua la ejecuccion
	@Override
	public void login(String user, String pwd) throws AuthException {
		try {
			emulator.enter();
			emulator.waitScreen(WindowIndicator.MUSIC_LOGIN_WINDOW, DEFAULT_TIMEOUT);

			writeLoginField(user, WindowIndicator.MUSIC_USERID_UNAUTHORIZED);
			writeLoginField(pwd, WindowIndicator.MUSIC_PWD_INCORRECT);

			if (emulator.syncBufferRead(DEFAULT_TIMEOUT)
									.contains(WindowIndicator.MUSIC_USERID_IN_USE.toString())) {
				throw new AuthException(WindowIndicator.MUSIC_USERID_IN_USE.toString());
			}

			emulator.enter();
			emulator.waitScreen(WindowIndicator.MUSIC_COMMAND_LINE, DEFAULT_TIMEOUT);
		} catch (InvalidScreenException ex) {
			throw new AuthException(INVALID_SCREEN_ERROR);
		} catch (IOException ex) {
			throw	new AuthException(IO_ERROR);
		}
	}

	@Override
	public void logout() throws AuthException {
		try {
			emulator.syncWrite(OFF);
			emulator.enter();
			emulator.waitScreen(WindowIndicator.MUSIC_MAIN_WINDOW, DEFAULT_TIMEOUT);
		} catch (InvalidScreenException ex) {
			throw new AuthException(INVALID_SCREEN_ERROR);
		} catch (IOException ex) {
			throw new AuthException(IO_ERROR);
		}
	}

	@Override
	public boolean executeJob(String jobName) {
		try {
			emulator.syncWrite(jobName);
			emulator.enter();
			emulator.waitScreen(appIndicator.get(jobName), DEFAULT_TIMEOUT);

			return true;
		} catch (InvalidScreenException ex) {
			System.err.println(INVALID_SCREEN_ERROR);
		} catch (IOException ex) {
			System.err.println(IO_ERROR);
		}
		return false;
	}
}
