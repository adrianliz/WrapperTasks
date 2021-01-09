/*
  TasksAppAPI.java
  09/01/2021
  @authors Adrián Lizaga Isaac, Borja Rando Jarque
 */
package domain;

import domain.exceptions.TasksAppException;

import java.util.Calendar;
import java.util.List;

public interface TasksAppAPI {
  void newTaskFile() throws TasksAppException;

  void addTask(Task task) throws TasksAppException;

  void removeTask(int idTask) throws TasksAppException;

  List<Task> searchTasks(Calendar date) throws TasksAppException;

  List<Task> listTasks() throws TasksAppException;

  void saveTasks() throws TasksAppException;

  void exit() throws TasksAppException;
}
