package application;

import domain.*;
import domain.enums.ActionWS3270;
import domain.enums.ScreenIndicator;
import domain.exceptions.InvalidScreenException;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ProxyWS3270 implements Proxy3270Emulator {
  private static final int MAX_ATTEMPTS_SEARCHING_INDICATOR = 10;
  private static final long DEFAULT_TIMEOUT = 2; // s

  private final InputStream in;
  private final PrintWriter out;

  public ProxyWS3270(String ws3270Path) throws IOException {
    Process ws3270 = Runtime.getRuntime().exec(ws3270Path);
    in = ws3270.getInputStream();
    out = new PrintWriter(ws3270.getOutputStream());
  }

  private Response3270 syncInputRead() throws IOException {
    StringBuilder buffer = new StringBuilder();

    while (in.available() == 0);

    while (in.available() > 0) {
      buffer.append((char) in.read());
    }

    return new ResponseWS3270(buffer.toString());
  }

  private Response3270 syncOutSend(String text) throws IOException {
    out.println(text);
    out.flush();

    return syncInputRead();
  }

  private Response3270 executeCommand(ActionWS3270 action) throws IOException {
    return syncOutSend(action.toString());
  }

  private Response3270 executeCommand(ActionWS3270 action, List<String> params) throws IOException {

    StringJoiner command = new StringJoiner(",", action + "(", ")");

    for (String param : params) {
      command.add(param);
    }
    return syncOutSend(command.toString());
  }

  public Response3270 connect(String ip, String port) throws IOException {
    List<String> params = new ArrayList<>();
    params.add(ip + ":" + port);

    return executeCommand(ActionWS3270.CONNECT, params);
  }

  public Response3270 disconnect() throws IOException {
    return executeCommand(ActionWS3270.DISCONNECT);
  }

  public Response3270 syncBufferRead(long timeout) throws IOException {
    List<String> params = new ArrayList<>();
    params.add(timeout + "");
    params.add(ActionWS3270.OUTPUT.toString());

    // This WAIT blocks ws3270 process and this process (because IN stream is blocking)
    // First ASCII is mandatory despite of WAIT implementation (see WS3270 documentation)
    Response3270 ascii = executeCommand(ActionWS3270.ASCII);
    if (executeCommand(ActionWS3270.WAIT, params).success()) {
      return executeCommand(ActionWS3270.ASCII);
    }

    return ascii;
  }

  @Override
  public Response3270 syncBufferRead() throws IOException {
    return syncBufferRead(DEFAULT_TIMEOUT);
  }

  public Response3270 syncWrite(String text) throws IOException {

    List<String> params = new ArrayList<>();
    params.add(text);
    return executeCommand(ActionWS3270.STRING, params);
  }

  public void waitScreen(ScreenIndicator indicator, long timeout)
      throws InvalidScreenException, IOException {

    int attempts = 0;
    boolean indicatorFound = false;

    do {
      Response3270 response = syncBufferRead(timeout);

      if (response.success()) {
        indicatorFound = response.getParsedData().contains(indicator.toString());
      }

      attempts++;
    } while ((!indicatorFound) && (attempts < MAX_ATTEMPTS_SEARCHING_INDICATOR));

    if (!indicatorFound) throw new InvalidScreenException(indicator);
  }

  @Override
  public void waitScreen(ScreenIndicator indicator) throws IOException, InvalidScreenException {
    waitScreen(indicator, DEFAULT_TIMEOUT);
  }

  public Response3270 clearFields() throws IOException {
    return executeCommand(ActionWS3270.ERASE_INPUT);
  }

  public Response3270 enter() throws IOException {
    return executeCommand(ActionWS3270.ENTER);
  }
}
