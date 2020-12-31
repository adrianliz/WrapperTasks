package domain.exceptions;

import domain.enums.ErrorMessage;
import domain.enums.ScreenIndicator;

public class InvalidScreenException extends Exception {
  public InvalidScreenException(ScreenIndicator indicator) {
    super("Error: Can't found indicator '" + indicator + "' in the screen");
  }

  public InvalidScreenException(ErrorMessage message) {
    super(message.toString());
  }
}
