package domain;

public interface MainframeAPI {
	boolean login(String user, String pwd);
	boolean logout();
	boolean executeJob(String jobName);
}
