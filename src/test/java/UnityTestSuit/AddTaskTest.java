package UnityTestSuit;

import application.Tasks2API;
import domain.Response3270;
import domain.Task;
import domain.TasksAppAPI;
import domain.enums.ErrorMessage;
import domain.enums.Job;
import domain.enums.ScreenIndicator;
import domain.exceptions.TaskNotValid;
import domain.exceptions.TasksAppException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class AddTaskTest {
	private static Response3270 defaultResponse;
	private static Response3270 taskNumberRepeated;

  @BeforeAll
  public static void setUpClass() {
    defaultResponse =
        new Response3270() {
          @Override
          public String getParsedData() {
            return "";
          }

          @Override
          public boolean contains(String indicator) {
            return indicator.equals("");
          }

          @Override
          public boolean success() {
            return true;
          }

          @Override
          public boolean isConnected() {
            return true;
          }
        };

		taskNumberRepeated = new Response3270() {
			@Override
			public String getParsedData() {
				return "";
			}

			@Override
			public boolean contains(String indicator) {
				return indicator.equals(ScreenIndicator.TASKS2_TASK_NUMBER_REPEATED.toString());
			}

			@Override
			public boolean success() {
				return true;
			}

			@Override
			public boolean isConnected() {
				return true;
			}
		};
		}

	@Test
	public void shouldAddTask() throws TaskNotValid {
		TasksAppAPI tasks2 = new Tasks2API(new Fake3270Emulator(defaultResponse),
                                       new FakeMainframeAPI(Job.TASKS2));

		Task t =
			new Task(101, "a", "a",
								Calendar.getInstance(), 12, 12);

		assertDoesNotThrow(() -> tasks2.addTask(t));
	}

	@Test
	public void shouldThrowTaskNameTooLong() {
		TaskNotValid ex = assertThrows(TaskNotValid.class, () ->
			new Task(101, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "a",
			Calendar.getInstance(), 1, 0));

		assertEquals(ex.getMessage(), ErrorMessage.TASK_NAME_TOO_LONG.toString());
	}

	@Test
	public void shouldThrowTaskDescriptionTooLong() {
		TaskNotValid ex = assertThrows(TaskNotValid.class, () ->
			new Task(101, "a", "a",
				Calendar.getInstance(), 1, 0));

		assertEquals(ex.getMessage(), ErrorMessage.TASK_DESC_TOO_LONG.toString());
	}

	@Test
	public void shouldThrowTaskNumberRepeated() throws TaskNotValid {
		TasksAppAPI tasks2 = new Tasks2API(new Fake3270Emulator(taskNumberRepeated),
																			 new FakeMainframeAPI(Job.TASKS2));

		Task t =
			new Task(101, "a", "a",
				Calendar.getInstance(), 12, 12);

		TasksAppException ex = assertThrows(TasksAppException.class, () -> tasks2.addTask(t));

		assertEquals(ex.getErrorMessage(), ErrorMessage.TASK_NUMBER_REPEATED);
	}
}
