/*
  ScreenIndicator.java
  09/01/2021
  @authors Adrián Lizaga Isaac, Borja Rando Jarque
 */
package domain.enums;

public enum ScreenIndicator {
  MUSIC_MAIN_WINDOW("Multi-User System for Interactive Computing"),
  MUSIC_LOGIN_WINDOW("sign on"),
  MUSIC_USERID_UNAUTHORIZED("Userid is not authorized"),
  MUSIC_PWD_INCORRECT("Password incorrect"),
  MUSIC_USERID_IN_USE("Userid is in use"),
  MUSIC_COMMAND_LINE("Full Screen Interface for MUSIC"),
  TASKS2_MAIN_WINDOW("TASK MANAGEMENT 2.0 BY TURO-SL SOFT"),
  TASKS2_NEW_TASK_FILE_WINDOW("**NEW TASK FILE**"),
  TASKS2_NEW_TASK_FILE_CREATED("NEW TASK FILE HAS BEEN CREATED"),
  TASKS2_ADD_TASK_WINDOW("**ADD TASK**"),
  TASKS2_TASK_NUMBER_REPEATED("TASK NUMBER REPEATED"),
  TASKS2_TASK_NAME_FIELD("TASK NAME"),
  TASKS2_REMOVE_TASK_WINDOW("**REMOVE TASK**"),
  TASKS2_TASK_NOT_FOUND("TASK NOT FOUND"),
  TASKS2_CONFIRMATION("CONFIRM (Y/N)"),
  TASKS2_LIST_TASKS_WINDOW("**LIST TASK**"),
  TASKS2_MORE("More"),
  TASKS2_TASKS_SAVED("TASKS HAVE BEEN SAVED"),
  TASKS2_PRESS_ENTER_TO_CONTINUE("**PRESS ENTER TO CONTINUE**"),
  TASKS2_BYE_WINDOW("**EXIT**");

  private final String indicator;

  ScreenIndicator(String indicator) {
    this.indicator = indicator;
  }

  @Override
  public String toString() {
    return indicator;
  }
}
