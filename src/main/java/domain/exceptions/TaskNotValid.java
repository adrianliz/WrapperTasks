package domain.exceptions;

import domain.enums.ErrorMessage;

public class TaskNotValid extends Exception {
  public TaskNotValid(ErrorMessage message) {
    super(message.toString());
  }
}
