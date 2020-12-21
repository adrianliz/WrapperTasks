package application;

import domain.MainframeAPI;
import domain.Proxy3270Emulator;
import domain.enums.ErrorMessage;
import domain.enums.Job;
import domain.enums.ScreenIndicator;
import domain.exceptions.InvalidScreenException;
import domain.exceptions.AuthException;

import java.io.IOException;
import java.util.*;

public class MusicAPI implements MainframeAPI {
	private static final Map<Job, ScreenIndicator> appIndicator = new EnumMap<>(Job.class);
	static {appIndicator.put(Job.TASKS2, ScreenIndicator.TASKS2_MAIN_WINDOW);}

	private static final String OFF = "off";

	private final List<Job> jobsExecuting;
	private final Proxy3270Emulator emulator;

	public MusicAPI(Proxy3270Emulator emulator) {
		this.emulator = emulator;
		jobsExecuting = new ArrayList<>();
	}

	private void writeLoginField(String field, ScreenIndicator errorIndicator, ErrorMessage message)
		throws IOException, AuthException {

		emulator.syncWrite(field);
		emulator.enter();

		if (emulator.syncBufferRead().contains(errorIndicator.toString())) {
			emulator.clearFields();
			throw new AuthException(message);
		}
	}

	// probar si cuando se hace throw se continua la ejecuccion
	@Override
	public void login(String user, String pwd) throws AuthException {
		try {
			emulator.enter();
			emulator.waitScreen(ScreenIndicator.MUSIC_LOGIN_WINDOW);

			writeLoginField(user, ScreenIndicator.MUSIC_USERID_UNAUTHORIZED,
											ErrorMessage.USERID_UNAUTHORIZED);
			writeLoginField(pwd, ScreenIndicator.MUSIC_PWD_INCORRECT,
										  ErrorMessage.PWD_ERROR);

			if (emulator.syncBufferRead()
									.contains(ScreenIndicator.MUSIC_USERID_IN_USE.toString())) {
				throw new AuthException(ErrorMessage.USERID_IN_USE);
			}

			emulator.enter();
			emulator.waitScreen(ScreenIndicator.MUSIC_COMMAND_LINE);
		} catch (InvalidScreenException ex) {
			throw new AuthException(ErrorMessage.INVALID_SCREEN);
		} catch (IOException ex) {
			throw	new AuthException(ErrorMessage.IO);
		}
	}

	@Override
	public void logout() throws AuthException {
		try {
			emulator.syncWrite(OFF);
			emulator.enter();
			emulator.waitScreen(ScreenIndicator.MUSIC_MAIN_WINDOW);
		} catch (InvalidScreenException ex) {
			throw new AuthException(ErrorMessage.INVALID_SCREEN);
		} catch (IOException ex) {
			throw new AuthException(ErrorMessage.IO);
		}
	}

	@Override
	public boolean executeJob(Job job) {
		try {
			emulator.syncWrite(job.toString());
			emulator.enter();
			emulator.waitScreen(appIndicator.get(job));

			return jobsExecuting.add(job);
		} catch (InvalidScreenException ex) {
			System.err.println(ErrorMessage.INVALID_SCREEN);
			System.err.println(ex.getMessage());
		} catch (IOException ex) {
			System.err.println(ErrorMessage.IO);
		}

		return false;
	}

	@Override
	public boolean finishJob(Job job) {
		return jobsExecuting.remove(job);
	}

	@Override
	public boolean isExecutingJob(Job job) {
		return jobsExecuting.contains(job);
	}
}
