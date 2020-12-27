package domain;

import domain.enums.ErrorMessage;
import domain.exceptions.TaskNotValid;

import java.util.Calendar;

public class Task {
  private final int id;
  private final String name;
  private final String description;
  private final Calendar date;

  public Task(
      int id,
      String name,
      String description,
      Calendar date,
      int maxNameLength,
      int maxDescriptionLength)
      throws TaskNotValid {

    validate(name, description, maxNameLength, maxDescriptionLength);

    this.id = id;
    this.name = name;
    this.description = description;
    this.date = date;
  }

  public String getId() {
    return String.valueOf(id);
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Calendar getDate() {
    return date;
  }

  private void validate(String name, String description, int maxNameLength, int maxDescLength)
      throws TaskNotValid {

    if (name.length() > maxNameLength) throw new TaskNotValid(ErrorMessage.TASK_NAME_TOO_LONG);
    if (description.length() > maxDescLength)
      throw new TaskNotValid(ErrorMessage.TASK_DESC_TOO_LONG);
  }
}
