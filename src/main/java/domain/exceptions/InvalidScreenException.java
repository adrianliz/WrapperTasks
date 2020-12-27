package domain.exceptions;

import domain.enums.ScreenIndicator;

public class InvalidScreenException extends Exception {
  public InvalidScreenException(ScreenIndicator indicator) {
    super("Can't found indicator: '" + indicator + "' in the screen");
  }
}
