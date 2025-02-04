/*
  SuccessIndicator.java
  09/01/2021
  @authors Adrián Lizaga Isaac, Borja Rando Jarque
 */
package domain.enums;

import java.util.regex.Pattern;

public enum SuccessIndicator {
  OK("^ok$"),
  ERROR("^error$"),
  UNDEFINED("^undefined$");

  private final String regexp;

  SuccessIndicator(String regexp) {
    this.regexp = regexp;
  }

  public static SuccessIndicator getSuccessIndicator(String s) {
    if (s != null) {
      for (SuccessIndicator successIndicator : SuccessIndicator.values()) {
        if (Pattern.compile(successIndicator.regexp()).matcher(s).matches()) {
          return successIndicator;
        }
      }
    }

    return UNDEFINED;
  }

  private String regexp() {
    return regexp;
  }
}
