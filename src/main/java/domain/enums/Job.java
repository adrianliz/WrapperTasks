package domain.enums;

public enum Job {
  TASKS2("tasks2.job", ScreenIndicator.TASKS2_MAIN_WINDOW);

  private final String jobName;
  private final ScreenIndicator indicator;

  Job(String jobName, ScreenIndicator indicator) {
    this.jobName = jobName;
    this.indicator = indicator;
  }

  public ScreenIndicator indicator() {
    return indicator;
  }

  @Override
  public String toString() {
    return jobName;
  }
}
