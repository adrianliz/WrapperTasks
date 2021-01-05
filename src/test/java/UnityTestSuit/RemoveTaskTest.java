package UnityTestSuit;

import application.Tasks2API;
import domain.Response3270;
import domain.TasksAppAPI;
import domain.enums.ErrorMessage;
import domain.enums.Job;
import domain.enums.ScreenIndicator;
import domain.exceptions.TasksAppException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RemoveTaskTest {
	private static Response3270 defaultResponse;
	private static Response3270 taskNotFound;

  @BeforeAll
  public static void setUpClass() {
    defaultResponse =
        new Response3270() {
          @Override
          public String getParsedData() {
            return "";
          }

          @Override
          public boolean contains(ScreenIndicator indicator) {
            return false;
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

		taskNotFound =
			new Response3270() {
				@Override
				public String getParsedData() {
					return "";
				}

				@Override
				public boolean contains(ScreenIndicator indicator) {
					return indicator.equals(ScreenIndicator.TASKS2_TASK_NOT_FOUND);
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
	public void shouldRemoveTask() {
		TasksAppAPI tasks2 = new Tasks2API(new Fake3270Emulator(defaultResponse),
																			 new FakeMainframeAPI(Job.TASKS2));

		assertDoesNotThrow(() -> tasks2.removeTask(1));
	}

	@Test
	public void shouldThrowTaskNotFound() {
		TasksAppAPI tasks2 = new Tasks2API(new Fake3270Emulator(taskNotFound),
																			 new FakeMainframeAPI(Job.TASKS2));

		TasksAppException ex = assertThrows(TasksAppException.class, () -> tasks2.removeTask(1));
		assertEquals(ex.getSimpleMessage(), ErrorMessage.TASK_NOT_FOUND.toString());
	}
}
