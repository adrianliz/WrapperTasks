package domain;

import java.io.IOException;

public interface MainframeAPI {
	boolean login(String user, String pwd) throws IOException, InterruptedException;
	boolean logout() throws IOException, InterruptedException;
	boolean executeJob(String jobName);
}
