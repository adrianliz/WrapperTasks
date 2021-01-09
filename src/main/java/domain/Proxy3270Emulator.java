/*
  Proxy3270Emulator.java
  09/01/2021
  @authors Adrián Lizaga Isaac, Borja Rando Jarque
 */
package domain;

import domain.enums.ScreenIndicator;
import domain.exceptions.InvalidScreenException;

import java.io.IOException;

public interface Proxy3270Emulator {
  Response3270 connect(String ip, String port) throws IOException;

  Response3270 disconnect() throws IOException;

  Response3270 syncBufferRead(long timeout) throws IOException;

  Response3270 syncBufferRead() throws IOException;

  Response3270 syncWrite(String text) throws IOException;

  void waitScreen(ScreenIndicator indicator, long timeout)
      throws InvalidScreenException, IOException;

  void waitScreen(ScreenIndicator indicator) throws InvalidScreenException, IOException;

  Response3270 enter() throws IOException;

  Response3270 clearFields() throws IOException;
}
