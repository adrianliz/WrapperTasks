/*
  Task.java
  09/01/2021
  @authors Adrián Lizaga Isaac, Borja Rando Jarque
 */
package domain;

import domain.enums.ErrorMessage;
import domain.exceptions.InvalidTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;

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
      throws InvalidTask {

    this.id = id;
    this.name = name;
    this.description = description;
    this.date = date;

    validate(maxNameLength, maxDescriptionLength);
  }

  public Task(Matcher taskFinder) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    id = Integer.parseInt(taskFinder.group(1));
    name = taskFinder.group(2);
    description = taskFinder.group(3);
    date = Calendar.getInstance();
    date.setTime(sdf.parse(taskFinder.group(4)));
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

  private void validate(int maxNameLength, int maxDescLength)
      throws InvalidTask {

    if (date == null) throw new InvalidTask(ErrorMessage.TASK_DATE_INVALID);
    if (name == null || name.length() == 0 || name.matches("\\s+"))
      throw new InvalidTask(ErrorMessage.TASK_NAME_INVALID);
    if (description == null || description.length() == 0 || description.matches("\\s+"))
      throw new InvalidTask(ErrorMessage.TASK_DESC_INVALID);
    if (name.length() > maxNameLength) throw new InvalidTask(ErrorMessage.TASK_NAME_TOO_LONG);
    if (description.length() > maxDescLength)
      throw new InvalidTask(ErrorMessage.TASK_DESC_TOO_LONG);
  }

  @Override
  public String toString() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    return id + " " + name + " " + description + " " + sdf.format(date.getTime());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Task task = (Task) o;

    return id == task.id;
  }

  @Override
  public int hashCode() {
    return id;
  }
}
