package domain.exceptions;

import domain.enums.ErrorMessage;
import domain.enums.Job;

public class TasksAppException extends Exception {
  private final String simpleMessage;

  public TasksAppException(Job job, ErrorMessage message) {
    super(job + " -> " + message.toString());

    simpleMessage = message.toString();
  }

  public TasksAppException(Job job, InvalidScreenException ex) {
    super(job + " -> " + ex.getMessage());

    this.simpleMessage = ex.getMessage();
  }

  public String getSimpleMessage() {
    return simpleMessage;
  }
}
