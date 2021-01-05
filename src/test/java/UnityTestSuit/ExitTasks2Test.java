package UnityTestSuit;

import application.Tasks2API;
import domain.TasksAppAPI;
import domain.enums.ErrorMessage;
import domain.enums.Job;
import domain.exceptions.TasksAppException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExitTasks2Test {
	@Test
	public void shouldExitTasks2() {
		TasksAppAPI tasks2 = new Tasks2API(new Fake3270Emulator(null),
																			 new FakeMainframeAPI(Job.TASKS2));

		assertDoesNotThrow(tasks2::exit);
	}

	@Test
	public void shouldThrowTasksExceptionNotRunning() {
		TasksAppAPI tasks2 = new Tasks2API(new Fake3270Emulator(null),
																			 new FakeMainframeAPI(null));

		TasksAppException ex = assertThrows(TasksAppException.class, tasks2::exit);
		assertEquals(ex.getSimpleMessage(), ErrorMessage.JOB_NOT_RUNNING.toString());
	}

	@Test
	public void shouldThrowTasksExceptionNotFinished() {
		TasksAppAPI tasks2 = new Tasks2API(new Fake3270Emulator(null),
																		   new FakeMainframeAPI(Job.TASKS2, true));

		TasksAppException ex = assertThrows(TasksAppException.class, tasks2::exit);
		assertEquals(ex.getSimpleMessage(), ErrorMessage.JOB_NOT_FINISHED.toString());
	}
}
