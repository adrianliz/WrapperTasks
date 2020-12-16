package application;

import domain.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import static java.lang.Thread.sleep;

public class ProxyWS3270 implements Proxy3270Emulator {
	private static final int WAIT_INPUT_AVAILABLE = 20; //ms
	private static final int MAX_ATTEMPTS_SEARCHING_INDICATOR = 10;

	private final InputStream in;
	private final PrintWriter out;

	public ProxyWS3270(String ws3270Path) throws IOException {
		Process ws3270 = Runtime.getRuntime().exec(ws3270Path);
		in = ws3270.getInputStream();
		out = new PrintWriter(ws3270.getOutputStream());
	}

	private Response3270 syncScreenRead()
		throws IOException, InterruptedException {

		StringBuilder buffer = new StringBuilder();

		while (in.available() == 0) {
			sleep(WAIT_INPUT_AVAILABLE);
		}

		while (in.available() > 0) {
			buffer.append((char) in.read());
		}

		return new ResponseWS3270(buffer.toString());
	}

	public boolean connect(String ip, String port) throws IOException, InterruptedException {
		return syncWrite(ActionWS3270.CONNECT + "(" + ip + ":" + port + ")").success();
	}

	public boolean disconnect() throws IOException, InterruptedException {
		return syncWrite(ActionWS3270.DISCONNECT.toString()).success();
	}

	public Response3270 syncBufferRead(long timeout) throws IOException, InterruptedException {
		Response3270 response =
			syncWrite(ActionWS3270.WAIT + "(" + timeout + ", " + ActionWS3270.OUTPUT + ")");

		if (response.success()) {
			return syncWrite(ActionWS3270.ASCII.toString());
		}

		return response;
	}

	public Response3270 syncWrite(String text)
		throws IOException, InterruptedException {

		out.println(text + "\n");
		out.flush();

		return syncScreenRead();
	}

	public void waitScreen(String indicator, long timeout)
		throws IOException, InterruptedException, IndicatorNotFoundException {

		int attempts = 0;
		boolean indicatorFound = false;

		do {
			Response3270 response = syncBufferRead(timeout);

			if (response.success()) {
				indicatorFound = response.getParsedData().contains(indicator);
			}

			attempts++;
		} while ((! indicatorFound) && (attempts < MAX_ATTEMPTS_SEARCHING_INDICATOR));

		if (! indicatorFound) throw new IndicatorNotFoundException("Can't found indicator: " + indicator);
	}

	public boolean enter() throws IOException, InterruptedException {
		return syncWrite(ActionWS3270.ENTER.toString()).success();
	}
}
