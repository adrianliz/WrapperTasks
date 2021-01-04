package application;

import domain.*;
import domain.enums.ActionS3270;
import domain.enums.ErrorMessage;
import domain.enums.ScreenIndicator;
import domain.exceptions.InvalidScreenException;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ProxyS3270 implements Proxy3270Emulator {
  private static final int MAX_ATTEMPTS_SEARCHING_INDICATOR = 10;
  private static final long DEFAULT_TIMEOUT = 3; // s

  private final Process s3270;
  private final InputStream in;
  private final PrintWriter out;

  public ProxyS3270(String ws3270Path) throws IOException {
    s3270 = Runtime.getRuntime().exec(ws3270Path);
    in = s3270.getInputStream();
    out = new PrintWriter(s3270.getOutputStream());
  }

  private Response3270 syncInputRead() throws IOException {
    StringBuilder buffer = new StringBuilder();

    while (in.available() == 0)
      ;

    while (in.available() > 0) {
      buffer.append((char) in.read());
    }

    return new ResponseS3270(buffer.toString());
  }

  private Response3270 syncOutSend(String text) throws IOException {
    out.println(text);
    out.flush();

    return syncInputRead();
  }

  private Response3270 executeCommand(ActionS3270 action) throws IOException {
    return syncOutSend(action.toString());
  }

  private Response3270 executeCommand(ActionS3270 action, List<String> params) throws IOException {

    StringJoiner command = new StringJoiner(",", action + "(", ")");

    for (String param : params) {
      command.add(param);
    }
    return syncOutSend(command.toString());
  }

  public Response3270 connect(String ip, String port) throws IOException {
    List<String> params = new ArrayList<>();
    params.add(ip + ":" + port);

    return executeCommand(ActionS3270.CONNECT, params);
  }

  public Response3270 disconnect() throws IOException {
    Response3270 response = executeCommand(ActionS3270.DISCONNECT);
    in.close();
    out.close();
    s3270.destroy();

    return response;
  }

  public Response3270 syncBufferRead(long timeout) throws IOException {
    List<String> params = new ArrayList<>();
    params.add(timeout + "");
    params.add(ActionS3270.OUTPUT.toString());

    // This WAIT blocks ws3270 process and this process (because IN stream is blocking)
    // First ASCII is mandatory despite of WAIT implementation (see WS3270 documentation)
    Response3270 ascii = executeCommand(ActionS3270.ASCII);
    if (executeCommand(ActionS3270.WAIT, params).success()) {
      return executeCommand(ActionS3270.ASCII);
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
    return executeCommand(ActionS3270.STRING, params);
  }

  public void waitScreen(ScreenIndicator indicator, long timeout)
      throws InvalidScreenException, IOException {

    int attempts = 0;
    boolean indicatorFound = false;

    do {
      Response3270 response = syncBufferRead(timeout);
      if (response.isConnected()) {
        if (response.success()) {
          indicatorFound = response.getParsedData().contains(indicator.toString());
        }
        attempts++;
      } else {
        throw new InvalidScreenException(ErrorMessage.PROXY_NOT_CONNECTED);
      }
    } while ((!indicatorFound) && (attempts < MAX_ATTEMPTS_SEARCHING_INDICATOR));

    if (!indicatorFound) throw new InvalidScreenException(indicator);
  }

  @Override
  public void waitScreen(ScreenIndicator indicator) throws IOException, InvalidScreenException {
    waitScreen(indicator, DEFAULT_TIMEOUT);
  }

  public Response3270 clearFields() throws IOException {
    return executeCommand(ActionS3270.ERASE_INPUT);
  }

  public Response3270 enter() throws IOException {
    return executeCommand(ActionS3270.ENTER);
  }
}
