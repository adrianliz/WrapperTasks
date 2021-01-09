/*
  Tasks2Option.java
  09/01/2021
  @authors Adrián Lizaga Isaac, Borja Rando Jarque
 */
package domain.enums;

public enum Tasks2Option {
  YES("Y"),
  NEW_TASK_FILE("N"),
  ADD_TASK("A"),
  REMOVE_TASK("R"),
  LIST_TASKS("L"),
  SAVE_TASKS("S"),
  EXIT("E");

  String option;

  Tasks2Option(String option) {
    this.option = option;
  }

  @Override
  public String toString() {
    return option;
  }
}
