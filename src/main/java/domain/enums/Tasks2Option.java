package domain.enums;

public enum Tasks2Option {
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
