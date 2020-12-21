package domain;

import java.util.Calendar;
import java.util.List;

public interface TasksAppAPI {
	void newTaskFile();
	boolean addTask(Task task);
	boolean removeTask(int idTask);
	List<Task> searchTasks(Calendar date);
	List<Task> listTasks();
	void saveTasks();
	boolean exit();
}
