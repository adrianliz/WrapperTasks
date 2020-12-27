package application;

import domain.*;
import domain.enums.ErrorMessage;
import domain.enums.Job;
import domain.enums.ScreenIndicator;
import domain.enums.Tasks2Option;
import domain.exceptions.InvalidScreenException;
import domain.exceptions.TasksAppException;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
    if (!mainframe.isExecutingJob(Job.TASKS2)) {
      throw new TasksAppException(Job.TASKS2, ErrorMessage.JOB_NOT_RUNNING);
    }
  }

  private void writeTaskField(String field, ScreenIndicator errorIndicator, ErrorMessage message)
      throws IOException, TasksAppException {

    emulator.syncWrite(field);
    emulator.enter();

    if (errorIndicator != null) {
      Response3270 response = emulator.syncBufferRead();
      if (response.contains(errorIndicator.toString())) {
        if (response.contains(ScreenIndicator.TASKS2_PRESS_ENTER_TO_CONTINUE.toString())) {
          emulator.enter();
        }
        throw new TasksAppException(Job.TASKS2, message);
      }
    }
  }

  private void writeTaskField(String field) throws IOException, TasksAppException {
    writeTaskField(field, null, null);
  }

  private String parseDate(Calendar date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yy");
    return "\""+ dateFormat.format(date.getTime()) + "\"";
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

  public void addTask(Task task) throws TasksAppException {
    validateTasks2Running();

    try {
      if (!emulator.syncBufferRead().contains(ScreenIndicator.TASKS2_ADD_TASK_WINDOW.toString())) {
        emulator.syncWrite(Tasks2Option.ADD_TASK.toString());
        emulator.enter();
        emulator.waitScreen(ScreenIndicator.TASKS2_ADD_TASK_WINDOW);
      }

      writeTaskField(task.getId(), ScreenIndicator.TASKS2_TASK_NUMBER_REPEATED,
                     ErrorMessage.TASK_NUMBER_REPEATED);
      writeTaskField(task.getName());
      writeTaskField(task.getDescription());
      writeTaskField(parseDate(task.getDate()));

      emulator.waitScreen(ScreenIndicator.TASKS2_PRESS_ENTER_TO_CONTINUE);
      emulator.enter();
      emulator.waitScreen(ScreenIndicator.TASKS2_MAIN_WINDOW);
    } catch (InvalidScreenException ex) {
      throw new TasksAppException(Job.TASKS2, ErrorMessage.INVALID_SCREEN);
    } catch (IOException ex) {
      throw new TasksAppException(Job.TASKS2, ErrorMessage.IO);
    }
  }

  public void removeTask(int idTask) throws TasksAppException {
    validateTasks2Running();

    try {
      emulator.syncWrite(Tasks2Option.REMOVE_TASK.toString());
      emulator.enter();
      emulator.waitScreen(ScreenIndicator.TASKS2_REMOVE_TASK_WINDOW);

      writeTaskField(
          String.valueOf(idTask),
          ScreenIndicator.TASKS2_TASK_NOT_FOUND,
          ErrorMessage.TASK_NOT_FOUND);

      emulator.waitScreen(ScreenIndicator.TASKS2_CONFIRMATION);
      emulator.syncWrite(Tasks2Option.YES.toString());
      emulator.enter();
      emulator.waitScreen(ScreenIndicator.TASKS2_PRESS_ENTER_TO_CONTINUE);
      emulator.enter();
      emulator.waitScreen(ScreenIndicator.TASKS2_MAIN_WINDOW);
    } catch (InvalidScreenException ex) {
      throw new TasksAppException(Job.TASKS2, ErrorMessage.INVALID_SCREEN);
    } catch (IOException ex) {
      throw new TasksAppException(Job.TASKS2, ErrorMessage.IO);
    }
  }

  public List<Task> searchTasks(Calendar date) {
    return null;
  }

  public List<Task> listTasks() {
    return null;
  }

  public void saveTasks() throws TasksAppException {
    validateTasks2Running();

    try {
      emulator.syncWrite(Tasks2Option.SAVE_TASKS.toString());
      emulator.enter();
      emulator.waitScreen(ScreenIndicator.TASKS2_TASKS_SAVED);
      emulator.enter();
      emulator.waitScreen(ScreenIndicator.TASKS2_MAIN_WINDOW);
    } catch (InvalidScreenException ex) {
      throw new TasksAppException(Job.TASKS2, ErrorMessage.INVALID_SCREEN);
    } catch (IOException ex) {
      throw new TasksAppException(Job.TASKS2, ErrorMessage.IO);
    }
  }

  public void exit() throws TasksAppException {
    saveTasks();

    try {
      emulator.syncWrite(Tasks2Option.EXIT.toString());
      emulator.enter();
      emulator.waitScreen(ScreenIndicator.TASKS2_BYE_WINDOW);
      emulator.enter();
      emulator.waitScreen(ScreenIndicator.MUSIC_COMMAND_LINE);

      if (!mainframe.finishJob(Job.TASKS2)) {
        throw new TasksAppException(Job.TASKS2, ErrorMessage.JOB_NOT_FINISHED);
      }
    } catch (InvalidScreenException ex) {
      throw new TasksAppException(Job.TASKS2, ErrorMessage.INVALID_SCREEN);
    } catch (IOException ex) {
      throw new TasksAppException(Job.TASKS2, ErrorMessage.IO);
    }
  }
}
