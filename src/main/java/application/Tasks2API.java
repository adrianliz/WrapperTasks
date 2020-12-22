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
import domain.exceptions.TasksAppException;

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

	private void validateTasks2Running() throws TasksAppException {
		if (! mainframe.isExecutingJob(Job.TASKS2)) {
			throw new TasksAppException(Job.TASKS2, ErrorMessage.JOB_NOT_RUNNING);
		}
	}

	public void newTaskFile() throws TasksAppException {
		validateTasks2Running();

		try {
			emulator.syncWrite(Tasks2Option.NEW_TASK_FILE.toString());
			emulator.enter();
			emulator.waitScreen(ScreenIndicator.TASKS2_NEW_TASK_FILE_WINDOW);
			emulator.syncWrite(Tasks2Option.YES.toString());
			emulator.enter();
			emulator.waitScreen(ScreenIndicator.TASKS2_NEW_TASK_FILE_CREATED);
			emulator.enter();
			emulator.waitScreen(ScreenIndicator.TASKS2_MAIN_WINDOW);
		} catch (InvalidScreenException ex) {
			throw new TasksAppException(Job.TASKS2, ErrorMessage.INVALID_SCREEN);
		} catch (IOException ex) {
			throw new TasksAppException(Job.TASKS2, ErrorMessage.IO);
		}
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

	public void exit() throws TasksAppException {
		validateTasks2Running();

		try {
			emulator.syncWrite(Tasks2Option.EXIT.toString());
			emulator.enter();
			emulator.waitScreen(ScreenIndicator.TASKS2_BYE_WINDOW);
			emulator.enter();
			emulator.waitScreen(ScreenIndicator.MUSIC_COMMAND_LINE);

			if (! mainframe.finishJob(Job.TASKS2)) {
				throw new TasksAppException(Job.TASKS2, ErrorMessage.JOB_NOT_FINISHED);
			}
		} catch (InvalidScreenException ex) {
			throw new TasksAppException(Job.TASKS2, ErrorMessage.INVALID_SCREEN);
		} catch (IOException ex) {
			throw new TasksAppException(Job.TASKS2, ErrorMessage.IO);
		}
	}
}
