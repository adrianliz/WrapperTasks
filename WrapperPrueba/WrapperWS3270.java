import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import static java.lang.Thread.sleep;

public class WrapperWS3270 {
	static  final int MAX_ATTEMPTS_WAITING_BUFFER_STATEMENT = 10;
	static final int WAIT_INPUT_STREAM_AVAILABLE = 5; //ms

	static final String OK = "ok";
	static final String MAIN_WINDOW_INDICATOR =
		"Press the ENTER key to view next page when you see this message --->  More...";
	static final String LOGIN_WINDOW_INDICATOR = "sign on";
	static final String POST_LOGIN_WINDOW_INDICATOR = "Press ENTER to continue...";
	static final String COMMAND_LINE_WINDOW_INDICATOR =
		"--------------------Full Screen Interface for MUSIC------------------";
	static final String LEGACY_APP_MENU_INDICATOR = "TASK MANAGEMENT 2.0 BY TURO-SL SOFT";

	static Process ws3270 = null;
	static InputStream in = null;
	static PrintWriter out = null;

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("You need to introduce IP and PORT");
			System.exit(1);
		}

		try {
			ws3270 = Runtime.getRuntime().exec("ws3270");
		} catch (IOException e) {
			System.out.println("Can't execute ws3270 process");
			e.printStackTrace();
			System.exit(1);
		}

		if (ws3270 != null) {
			in = ws3270.getInputStream();
			out = new PrintWriter(new OutputStreamWriter(ws3270.getOutputStream()));

			if (connect(args[0], args[1])) {
				System.out.println("Connected!");
				if (login()) {
					System.out.println("Login successful!");

					if (executeLegacyApp()) {
						System.out.println("Executing legacy app!");

						if (exitLegacyApp()) {
							System.out.println("Exit of legacy app!");
						}
					}

					if (logout()) {
						System.out.println("Logout successful!");
					}
				}

				if (disconnect()) {
					System.out.println("Disconnected!");
				}
			}
		}
	}

	public static boolean connect(String ip, String port) {
		return ((executeCommand("connect(" + ip + ":" + port + ")").contains(OK))) &&
						(bufferHasStatement(MAIN_WINDOW_INDICATOR));
	}

	public static boolean login() {
		return ((executeCommand("enter").contains(OK)) &&
						(bufferHasStatement(LOGIN_WINDOW_INDICATOR)) &&
						(executeCommand("string(\"prog\nprog123\n\")").contains(OK)) &&
						(bufferHasStatement(POST_LOGIN_WINDOW_INDICATOR)) &&
						(executeCommand("enter").contains(OK)) &&
						(bufferHasStatement(COMMAND_LINE_WINDOW_INDICATOR)));
	}

	public static boolean executeLegacyApp() {
		return ((executeCommand("string(\"tasks2.job\n\")").contains(OK)) &&
						(bufferHasStatement(LEGACY_APP_MENU_INDICATOR)));
	}

	public static boolean exitLegacyApp() {
		return ((executeCommand("string(\"e\n\n\")").contains(OK)) &&
						(bufferHasStatement(COMMAND_LINE_WINDOW_INDICATOR)));
	}

	public static boolean logout() {
		return ((executeCommand("string(\"off\n\")").contains(OK)) &&
						(bufferHasStatement(MAIN_WINDOW_INDICATOR)));
	}

	public static boolean disconnect() {
		return executeCommand("disconnect").contains(OK);
	}

	public static String executeCommand(String command) {
		out.println(command);
		out.flush();

		return readScreen(); //return result of command on screen
	}

	public static String readScreen() {
		StringBuilder buffer = new StringBuilder();

		try {
			while (in.available() == 0) {
				sleep(WAIT_INPUT_STREAM_AVAILABLE);
			}

			while (in.available() > 0) {
				buffer.append((char) in.read());
			}
		} catch (IOException e) {
			System.out.println("Error while reading from s3270 input stream");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Process was interrupt while waiting s3270 input stream");
			e.printStackTrace();
		}

		return buffer.toString();
	}

	public static boolean bufferHasStatement(String statement) {
		int attempts = 0;
		boolean found = false;

		do {
			//funciona por que executeCommand espera a que salga el resultado por pantalla y por lo tanto
			//se sincroniza el proceso de Java y el de ws3270
			//sustituir en la versión definitiva el 5 hardcodeado, por una constante!
			long msIni = System.currentTimeMillis();
			if (executeCommand("wait(5, output)").contains(OK)) { //wait buffer change since last invocation of ascii
				String buffer = executeCommand("ascii");
				System.out.println(buffer);
				found = buffer.contains(statement);
			}
			long msFin = System.currentTimeMillis();
			System.out.println("Time -> " + (msFin - msIni) + " ms");
			System.out.println("Attempts -> " + attempts);

			attempts++;
		} while ((! found) && (attempts < MAX_ATTEMPTS_WAITING_BUFFER_STATEMENT));

		return found;
	}
}
