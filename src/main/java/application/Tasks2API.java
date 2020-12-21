package application;

import domain.Proxy3270Emulator;
import domain.MainframeAPI;
import domain.Task;
import domain.TasksAppAPI;
import domain.enums.ErrorMessage;
import domain.enums.Job;
import domain.enums.ScreenIndicator;
import domain.enums.Tasks2Option;
import domain.exceptions.InvalidScreenException;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class Tasks2API implements TasksAppAPI {
	private final Proxy3270Emulator emulator;
	private final MainframeAPI mainframe;

	public Tasks2API(Proxy3270Emulator emulator, MainframeAPI mainframe) {
		this.emulator = emulator;
		this.mainframe = mainframe;
	}

	public void newTaskFile() {

	}

	public boolean addTask(Task task) {
		return false;
	}

	public boolean removeTask(int idTask) {
		return false;
	}

	public List<Task> searchTasks(Calendar date) {
		return null;
	}

	public List<Task> listTasks() {
		return null;
	}

	public void saveTasks() {

	}

	public boolean exit() {
		try {
			if (mainframe.isExecutingJob(Job.TASKS2)) {
				emulator.syncWrite(Tasks2Option.EXIT.toString());
				emulator.enter();
				emulator.waitScreen(ScreenIndicator.TASKS2_BYE_WINDOW);
				emulator.enter();
				emulator.waitScreen(ScreenIndicator.MUSIC_COMMAND_LINE);

				return mainframe.finishJob(Job.TASKS2);
			}
		} catch (InvalidScreenException ex) {
			System.err.println(ErrorMessage.INVALID_SCREEN);
			System.err.println(ex.getMessage());
		} catch (IOException ex) {
			System.err.println(ErrorMessage.IO);
		}

		return false;
	}
}
