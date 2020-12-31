package domain.enums;

public enum ErrorMessage {
  PROXY_NOT_CONNECTED("Error: Proxy is not connected. Check IP and PORT"),
  USERID_UNAUTHORIZED("Error: UserID is unauthorized"),
  PWD_INCORRECT("Error: Password incorrect"),
  USERID_IN_USE("Error: UserID is in use"),
  TASK_NOT_FOUND("Error: Task not found"),
  TASK_NUMBER_REPEATED("Error: Task number repeated"),
  TASK_NAME_TOO_LONG("Error: Task name too long"),
  TASK_DESC_TOO_LONG("Error: Task description too long"),
  TASKS_PARSE("Error: Can't parse raw tasks"),
  JOB_NOT_RUNNING("Error: Job isn't running"),
  JOB_NOT_FINISHED("Error: Can't finish job"),
  IO("Error: An IO error occurred");

  private final String message;

  ErrorMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return message;
  }
}
