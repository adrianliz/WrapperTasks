package application;

import domain.Proxy3270Emulator;
import domain.MainframeAPI;
import domain.Task;
import domain.TasksAppAPI;

import java.util.Calendar;
import java.util.List;

public class Tasks2API implements TasksAppAPI {
	private Proxy3270Emulator emulator;
	private MainframeAPI mainframe;

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

	public void exit() {

	}
}
