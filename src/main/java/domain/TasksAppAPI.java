package domain;

import domain.exceptions.TasksAppException;

import java.util.Calendar;
import java.util.List;

public interface TasksAppAPI {
	void newTaskFile() throws TasksAppException;
	boolean addTask(Task task) throws TasksAppException;
	boolean removeTask(int idTask) throws TasksAppException;
	List<Task> searchTasks(Calendar date);
	List<Task> listTasks();
	void saveTasks() throws TasksAppException;
	void exit() throws TasksAppException;
}
