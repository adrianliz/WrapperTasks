/*
  AuthException.java
  09/01/2021
  @authors Adrián Lizaga Isaac, Borja Rando Jarque
 */
package domain.exceptions;

import domain.enums.ErrorMessage;

public class AuthException extends Exception {
  public AuthException(ErrorMessage message) {
    super(message.toString());
  }

  public AuthException(InvalidScreenException ex) {
    super(ex.getMessage());
  }
}
