package domain;

import java.util.regex.Pattern;

public enum SuccesIndicator {
	OK("^ok$"),
	ERROR("^error$"),
	UNDEFINED("^undefined$");

	private final String regexp;

	SuccesIndicator(String regexp) {
		this.regexp = regexp;
	}

	public static SuccesIndicator getSuccessIndicator(String s) {
		for (SuccesIndicator succesIndicator: SuccesIndicator.values()) {
			if (Pattern.compile(succesIndicator.regexp()).matcher(s).matches()) {
				return succesIndicator;
			}
		}

		return UNDEFINED;
	}

	private String regexp() {
		return regexp;
	}
}
