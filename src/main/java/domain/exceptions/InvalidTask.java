/*
  InvalidTask.java
  09/01/2021
  @authors Adrián Lizaga Isaac, Borja Rando Jarque
 */
package domain.exceptions;

import domain.enums.ErrorMessage;

public class InvalidTask extends Exception {
  public InvalidTask(ErrorMessage message) {
    super(message.toString());
  }
}
