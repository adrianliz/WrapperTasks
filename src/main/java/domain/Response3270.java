package domain;

public interface Response3270 {
	String getParsedData();
	boolean contains(String indicator);
	boolean success();
	boolean isConnected();
}
