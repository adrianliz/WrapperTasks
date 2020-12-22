package domain.enums;

public enum Tasks2Option {
	YES("Y"),
	NEW_TASK_FILE("N"),
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
