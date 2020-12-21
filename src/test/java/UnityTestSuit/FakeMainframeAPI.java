package UnityTestSuit;

import domain.MainframeAPI;
import domain.enums.Job;

import java.util.ArrayList;
import java.util.List;

public class FakeMainframeAPI implements MainframeAPI {
	private final List<Job> jobsExecuting;

	FakeMainframeAPI(Job job) {
		jobsExecuting = new ArrayList<>();
		jobsExecuting.add(job);
	}

	@Override
	public void login(String user, String pwd) {

	}

	@Override
	public void logout() {

	}

	@Override
	public boolean executeJob(Job job) {
		return jobsExecuting.add(job);
	}

	@Override
	public boolean finishJob(Job job) {
		return jobsExecuting.remove(job);
	}

	@Override
	public boolean isExecutingJob(Job job) {
		return jobsExecuting.contains(job);
	}
}
