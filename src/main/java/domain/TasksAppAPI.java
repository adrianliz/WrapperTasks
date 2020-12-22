package domain;

import domain.exceptions.TasksAppException;

import java.util.Calendar;
import java.util.List;

public interface TasksAppAPI {
	void newTaskFile() throws TasksAppException;
	boolean addTask(Task task);
	boolean removeTask(int idTask);
	List<Task> searchTasks(Calendar date);
	List<Task> listTasks();
	void saveTasks();
	void exit() throws TasksAppException;
}
