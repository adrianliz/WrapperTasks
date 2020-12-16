package domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class ResponseWS3270 implements Response3270 {
	private static final String DATA_INDICATOR = "data: ";
	private static final String CONNECTED_INDICATOR = "C";

	private String data;
	private String prompt;
	private SuccesIndicator succesIndicator;

	public ResponseWS3270(String rawResponse) throws IOException{
		if (rawResponse != null) {
			BufferedReader screenReader =
				new BufferedReader(new StringReader(rawResponse));
			StringBuilder dataBuilder = new StringBuilder();
			String line;

			while ((line = screenReader.readLine()).startsWith(DATA_INDICATOR)) {
				dataBuilder.append(line);
				dataBuilder.append(" ");
			}

			data = dataBuilder.toString().replace(DATA_INDICATOR, "");
			prompt = line;
			succesIndicator = SuccesIndicator.getSuccessIndicator(screenReader.readLine());
		}
	}

	public String getParsedData() {
		return data;
	}

	public boolean success() {
		return succesIndicator.equals(SuccesIndicator.OK);
	}

	public boolean isConnected() {
		return prompt.contains(CONNECTED_INDICATOR);
	}
}
