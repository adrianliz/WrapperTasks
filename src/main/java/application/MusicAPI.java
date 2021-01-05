package application;

import domain.MainframeAPI;
import domain.Proxy3270Emulator;
import domain.enums.ErrorMessage;
import domain.enums.Job;
import domain.enums.ScreenIndicator;
import domain.exceptions.InvalidScreenException;
import domain.exceptions.AuthException;

import java.io.IOException;
import java.util.*;

public class MusicAPI implements MainframeAPI {
  private static final String OFF = "off";

  private final Proxy3270Emulator emulator;
  private final List<Job> jobsExecuting;

  public MusicAPI(Proxy3270Emulator emulator) {
    this.emulator = emulator;
    jobsExecuting = new ArrayList<>();
  }

  private void writeLoginField(String field, ScreenIndicator errorIndicator, ErrorMessage message)
      throws IOException, AuthException {

    emulator.syncWrite(field);
    emulator.enter();

    if (emulator.syncBufferRead().contains(errorIndicator)) {
      emulator.clearFields();
      throw new AuthException(message);
    }
  }

  @Override
  public void login(String user, String pwd) throws AuthException {
    try {
      emulator.enter();
      emulator.waitScreen(ScreenIndicator.MUSIC_LOGIN_WINDOW);

      writeLoginField(
          user, ScreenIndicator.MUSIC_USERID_UNAUTHORIZED, ErrorMessage.USERID_UNAUTHORIZED);
      writeLoginField(pwd, ScreenIndicator.MUSIC_PWD_INCORRECT, ErrorMessage.PWD_INCORRECT);

      if (emulator.syncBufferRead().contains(ScreenIndicator.MUSIC_USERID_IN_USE)) {
        throw new AuthException(ErrorMessage.USERID_IN_USE);
      }

      emulator.enter();
      emulator.waitScreen(ScreenIndicator.MUSIC_COMMAND_LINE);
    } catch (InvalidScreenException ex) {
      throw new AuthException(ex);
    } catch (IOException ex) {
      throw new AuthException(ErrorMessage.IO);
    }
  }

  @Override
  public void logout() throws AuthException {
    try {
      emulator.syncWrite(OFF);
      emulator.enter();
      emulator.waitScreen(ScreenIndicator.MUSIC_MAIN_WINDOW);
    } catch (InvalidScreenException ex) {
      throw new AuthException(ex);
    } catch (IOException ex) {
      throw new AuthException(ErrorMessage.IO);
    }
  }

  @Override
  public void executeJob(Job job) throws InvalidScreenException, IOException {
    emulator.syncWrite(job.toString());
    emulator.enter();
    emulator.waitScreen(job.indicator());

    jobsExecuting.add(job);
  }

  @Override
  public boolean finishJob(Job job) {
    return jobsExecuting.remove(job);
  }

  @Override
  public boolean isExecutingJob(Job job) {
    return jobsExecuting.contains(job);
  }
}
