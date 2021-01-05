package domain.exceptions;

import domain.enums.ErrorMessage;

public class InvalidTask extends Exception {
  public InvalidTask(ErrorMessage message) {
    super(message.toString());
  }
}
