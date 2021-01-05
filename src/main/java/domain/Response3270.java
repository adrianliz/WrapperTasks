package domain;

import domain.enums.ScreenIndicator;

public interface Response3270 {
  String getParsedData();

  boolean contains(ScreenIndicator indicator);

  boolean success();

  boolean isConnected();
}
