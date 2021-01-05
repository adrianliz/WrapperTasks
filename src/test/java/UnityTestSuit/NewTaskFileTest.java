package UnityTestSuit;

import application.Tasks2API;
import domain.TasksAppAPI;
import domain.enums.ErrorMessage;
import domain.enums.Job;
import domain.exceptions.TasksAppException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NewTaskFileTest {

	@Test
	public void shouldCreateNewTaskFile() {
		TasksAppAPI tasks2 = new Tasks2API(new Fake3270Emulator(null),
																       new FakeMainframeAPI(Job.TASKS2));

		assertDoesNotThrow(tasks2::newTaskFile);
	}

	@Test
	public void shouldThrowTasksExceptionNotRunning() {
		TasksAppAPI tasks2 = new Tasks2API(new Fake3270Emulator(null),
																	     new FakeMainframeAPI(null));

		TasksAppException ex = assertThrows(TasksAppException.class, tasks2::newTaskFile);
		assertEquals(ex.getSimpleMessage(), ErrorMessage.JOB_NOT_RUNNING.toString());
	}
}
