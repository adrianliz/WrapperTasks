package UnityTestSuit;

import application.Tasks2API;
import domain.MainframeAPI;
import domain.TasksAppAPI;
import domain.enums.Job;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExitTasks2Test {
	@Test
	public void shouldExitTasks2() {
		TasksAppAPI tasks2 = new Tasks2API(new Fake3270Emulator(), new FakeMainframeAPI(Job.TASKS2));
		assertTrue(tasks2.exit());
	}

	@Test
	public void shouldNotExitTasks2() {
		TasksAppAPI tasks2 = new Tasks2API(new Fake3270Emulator(), new FakeMainframeAPI(null));
		assertFalse(tasks2.exit());
	}
}
