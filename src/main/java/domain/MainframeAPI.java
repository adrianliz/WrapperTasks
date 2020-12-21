package domain;

import domain.enums.Job;
import domain.exceptions.AuthException;

public interface MainframeAPI {
	void login(String user, String pwd) throws AuthException;
	void logout() throws AuthException;
	boolean executeJob(Job job);
	boolean finishJob(Job job);
	boolean isExecutingJob(Job job);
}
