/*
  Fake3270Emulator.java
  09/01/2021
  @author Adrián Lizaga Isaac
 */
package UnityTestSuit;

import domain.Proxy3270Emulator;
import domain.Response3270;
import domain.enums.ErrorMessage;
import domain.enums.ScreenIndicator;
import domain.exceptions.InvalidScreenException;

public class Fake3270Emulator implements Proxy3270Emulator {
	private final Response3270 defaultResponse;
	private final boolean waitScreenFail;

	Fake3270Emulator(Response3270 defaultResponse) {
		this.defaultResponse = defaultResponse;
		waitScreenFail = false;
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
	public Response3270 syncBufferRead() {
		return defaultResponse;
	}

	@Override
	public Response3270 syncWrite(String text) {
		return defaultResponse;
	}

	@Override
	public void waitScreen(ScreenIndicator indicator, long timeout) throws InvalidScreenException {
		if (waitScreenFail) throw new InvalidScreenException(ErrorMessage.PROXY_NOT_CONNECTED);
	}

	@Override
	public void waitScreen(ScreenIndicator indicator) throws InvalidScreenException {
		waitScreen(indicator, 0);
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
