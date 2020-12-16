package domain;

import domain.enums.WindowIndicator;
import domain.exceptions.InvalidScreenException;

import java.io.IOException;

public interface Proxy3270Emulator {
	Response3270 connect(String ip, String port) throws IOException;
	Response3270 disconnect() throws IOException;
	Response3270 syncBufferRead(long timeout) throws IOException;
	Response3270 syncWrite(String text) throws IOException;
	void waitScreen(WindowIndicator indicator, long timeout) throws IOException, InvalidScreenException;
	Response3270 enter() throws IOException;
	Response3270 clearFields() throws IOException;
}
