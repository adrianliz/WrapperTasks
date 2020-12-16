package domain;

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

	boolean isValid(int maxNameLength, int maxDescriptionLength) {
		return ((name.length() <= maxNameLength) &&
						(description.length() <= maxDescriptionLength));
	}
}
