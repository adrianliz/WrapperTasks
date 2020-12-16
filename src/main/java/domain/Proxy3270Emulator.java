package domain;

import java.io.IOException;

public interface Proxy3270Emulator {
	Response3270 connect(String ip, String port) throws IOException, InterruptedException ;
	Response3270 disconnect() throws IOException, InterruptedException;
	Response3270 syncBufferRead(long timeout) throws IOException, InterruptedException;
	Response3270 syncWrite(String text) throws IOException, InterruptedException;
	boolean waitScreen(String indicator, long timeout) throws IOException, InterruptedException;
	Response3270 enter() throws IOException, InterruptedException;
}
