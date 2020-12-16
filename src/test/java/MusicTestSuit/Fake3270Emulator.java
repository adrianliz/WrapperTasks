package MusicTestSuit;

import domain.Proxy3270Emulator;
import domain.Response3270;
import domain.enums.WindowIndicator;

public class Fake3270Emulator implements Proxy3270Emulator {
	private final Response3270 defaultResponse;

	public Fake3270Emulator(Response3270 defaultResponse) {
		this.defaultResponse = defaultResponse;
	}

	@Override
	public Response3270 connect(String ip, String port) {
		return defaultResponse;
	}

	@Override
	public Response3270 disconnect() {
		return defaultResponse;
	}

	@Override
	public Response3270 syncBufferRead(long timeout) {
		return defaultResponse;
	}

	@Override
	public Response3270 syncWrite(String text) {
		return defaultResponse;
	}

	@Override
	public void waitScreen(WindowIndicator indicator, long timeout) {}

	@Override
	public Response3270 enter() {
		return defaultResponse;
	}

	@Override
	public Response3270 clearFields() {
		return defaultResponse;
	}
}
