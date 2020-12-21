package domain.enums;

public enum Job {
	TASKS2("tasks2.job");

	private final String jobName;

	Job(String jobName) {
		this.jobName = jobName;
	}

	@Override
	public String toString() {
		return jobName;
	}
}
