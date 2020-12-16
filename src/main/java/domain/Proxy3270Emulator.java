package domain;

import java.io.IOException;

public interface Proxy3270Emulator {
	boolean connect(String ip, String port) throws IOException, InterruptedException ;
	boolean disconnect() throws IOException, InterruptedException;
	Response3270 syncBufferRead(long timeout) throws IOException, InterruptedException;
	Response3270 syncWrite(String text) throws IOException, InterruptedException;
	void waitScreen(String indicator, long timeout)
		throws IOException, InterruptedException, IndicatorNotFoundException;
	boolean enter() throws IOException, InterruptedException;
}
