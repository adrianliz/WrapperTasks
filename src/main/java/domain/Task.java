package domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Task {

	private final int id;
	private final String name;
	private final String description;
	private final Calendar date;

	public Task(int id, String name, String description, Calendar date) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.date = date;
	}

	public String getId() {
		return Integer.valueOf(id).toString();
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

	// probablemente sea mejor que el mainframe nos diga cuando no es válida
	boolean isValid(int maxNameLength, int maxDescriptionLength) {
		return ((name.length() <= maxNameLength) &&
						(description.length() <= maxDescriptionLength));
	}

}
