package domain;

import domain.enums.ErrorMessage;
import domain.exceptions.TaskNotValid;
import jdk.swing.interop.SwingInterOpUtils;

import java.io.BufferedReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Pattern;

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

    // TODO: comprobar que el date sea valido
    validate(name, description, maxNameLength, maxDescriptionLength);

    this.id = id;
    this.name = name;
    this.description = description;
    this.date = date;
  }

  public Task(Scanner sc) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

    id = Integer.parseInt(parseTaskField(sc));
    name = parseTaskField(sc);
    description = parseTaskField(sc);
    date = Calendar.getInstance();

    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.contains("DATE: ")) {
        date.setTime(sdf.parse(parseTaskField(new Scanner(s))));
      }
    }
  }

  private String parseTaskField(Scanner sc) {
    return sc.nextLine().replaceAll(Pattern.compile(".*:\\s").pattern(), "").
                         replaceAll(Pattern.compile("\\s\\s+").pattern(), "");
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

  @Override
  public String toString() {
    return "Task{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", description='" + description + '\'' +
      '}';
  }
}
