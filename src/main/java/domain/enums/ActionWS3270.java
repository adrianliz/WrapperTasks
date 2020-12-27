package domain.enums;

public enum ActionWS3270 {
  ASCII("ascii"),
  CONNECT("connect"),
  DISCONNECT("disconnect"),
  ENTER("enter"),
  ERASE_INPUT("eraseinput"),
  OUTPUT("output"),
  STRING("string"),
  WAIT("wait");

  private final String action;

  ActionWS3270(String action) {
    this.action = action;
  }

  @Override
  public String toString() {
    return action;
  }
}
