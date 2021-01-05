package domain;

import domain.enums.ScreenIndicator;
import domain.enums.SuccessIndicator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class ResponseS3270 implements Response3270 {
  private static final String DATA_INDICATOR = "data:";
  private static final String CONNECTED_INDICATOR = "C";

  private String data;
  private String prompt;
  private SuccessIndicator successIndicator;

  public ResponseS3270(String rawResponse) throws IOException {
    if (rawResponse != null) {
      BufferedReader screenReader = new BufferedReader(new StringReader(rawResponse));
      StringBuilder dataBuilder = new StringBuilder();
      String line;

      while (((line = screenReader.readLine()) != null) &&
              (line.startsWith(DATA_INDICATOR))) {
        dataBuilder.append(line);
        dataBuilder.append("\n");
      }

      data = dataBuilder.toString();
      prompt = line;
      successIndicator = SuccessIndicator.getSuccessIndicator(screenReader.readLine());
    }
  }

  public String getParsedData() {
    if (data != null) {
      return data.replaceAll(DATA_INDICATOR + "\\s", "");
    }

    return "";
  }

  public boolean contains(ScreenIndicator indicator) {
    if (indicator != null) {
      return data.contains(indicator.toString());
    }

    return false;
  }

  public boolean success() {
    return successIndicator.equals(SuccessIndicator.OK);
  }

  public boolean isConnected() {
    if (prompt != null) {
      return prompt.contains(CONNECTED_INDICATOR);
    }

    return false;
  }
}
