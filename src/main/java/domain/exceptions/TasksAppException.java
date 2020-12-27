package domain.exceptions;

import domain.enums.ErrorMessage;
import domain.enums.Job;

public class TasksAppException extends Exception {
  private final ErrorMessage message;

  public TasksAppException(Job job, ErrorMessage message) {
    super("Error occurred in " + job + "\n" + message.toString());

    this.message = message;
  }

  public ErrorMessage getErrorMessage() {
    return message;
  }
}
