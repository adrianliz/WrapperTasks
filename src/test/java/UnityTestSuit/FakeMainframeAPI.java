/*
  FakeMainframeAPI.java
  09/01/2021
  @author Borja Rando Jarque
 */
package UnityTestSuit;

import domain.MainframeAPI;
import domain.enums.Job;

import java.util.ArrayList;
import java.util.List;

public class FakeMainframeAPI implements MainframeAPI {
	private final List<Job> jobsExecuting;
	private final boolean finishJobFail;

	FakeMainframeAPI(Job job) {
		jobsExecuting = new ArrayList<>();
		jobsExecuting.add(job);

		finishJobFail = false;
	}

	FakeMainframeAPI(Job job, boolean finishJobFail) {
		jobsExecuting = new ArrayList<>();
		jobsExecuting.add(job);

		this.finishJobFail = finishJobFail;
	}

	@Override
	public void login(String user, String pwd) {

	}

	@Override
	public void logout() {

	}

	@Override
	public void executeJob(Job job) {
		jobsExecuting.add(job);
	}

	@Override
	public boolean finishJob(Job job) {
		return ! finishJobFail;
	}

	@Override
	public boolean isExecutingJob(Job job) {
		return jobsExecuting.contains(job);
	}
}
