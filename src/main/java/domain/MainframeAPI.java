package domain;

import domain.enums.Job;
import domain.exceptions.AuthException;
import domain.exceptions.InvalidScreenException;

import java.io.IOException;

public interface MainframeAPI {
	void login(String user, String pwd) throws AuthException;
	void logout() throws AuthException;
	void executeJob(Job job) throws InvalidScreenException, IOException;
	boolean finishJob(Job job);
	boolean isExecutingJob(Job job);
}
