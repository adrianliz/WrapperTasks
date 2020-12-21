package UnityTestSuit;

import domain.Proxy3270Emulator;
import domain.Response3270;
import domain.enums.ScreenIndicator;

public class Fake3270Emulator implements Proxy3270Emulator {
	private final Response3270 defaultResponse;

	public Fake3270Emulator(Response3270 defaultResponse) {
		this.defaultResponse = defaultResponse;
	}
	public Fake3270Emulator() { this.defaultResponse = null; }

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
	public Response3270 syncBufferRead() {
		return defaultResponse;
	}

	@Override
	public Response3270 syncWrite(String text) {
		return defaultResponse;
	}

	@Override
	public void waitScreen(ScreenIndicator indicator, long timeout) {}

	@Override
	public void waitScreen(ScreenIndicator indicator) {

	}

	@Override
	public Response3270 enter() {
		return defaultResponse;
	}

	@Override
	public Response3270 clearFields() {
		return defaultResponse;
	}
}
