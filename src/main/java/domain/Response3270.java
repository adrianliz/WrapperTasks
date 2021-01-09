/*
  Response3270.java
  09/01/2021
  @authors Adrián Lizaga Isaac, Borja Rando Jarque
 */
package domain;

import domain.enums.ScreenIndicator;

public interface Response3270 {
  String getParsedData();

  boolean contains(ScreenIndicator indicator);

  boolean success();

  boolean isConnected();
}
