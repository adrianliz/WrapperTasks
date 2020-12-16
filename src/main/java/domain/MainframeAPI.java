package domain;

import domain.exceptions.AuthException;

public interface MainframeAPI {
	void login(String user, String pwd) throws AuthException;
	void logout() throws AuthException;
	boolean executeJob(String jobName);
}
