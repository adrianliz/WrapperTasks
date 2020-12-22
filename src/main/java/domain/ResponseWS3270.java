package domain;

import domain.enums.SuccesIndicator;

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
				dataBuilder.append("\n");
			}

			data = dataBuilder.toString();
			prompt = line;
			succesIndicator = SuccesIndicator.getSuccessIndicator(screenReader.readLine());
		}
	}

	// TODO: return parse data and not raw data
	public String getParsedData() {
		return data;
	}

	public boolean contains(String indicator) { return data.contains(indicator); }

	public boolean success() {
		return succesIndicator.equals(SuccesIndicator.OK);
	}

	public boolean isConnected() {
		return prompt.contains(CONNECTED_INDICATOR);
	}
}
