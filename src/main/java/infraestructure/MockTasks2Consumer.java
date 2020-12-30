package infraestructure;

import application.MusicAPI;
import application.ProxyWS3270;
import application.Tasks2API;
import domain.MainframeAPI;
import domain.Proxy3270Emulator;
import domain.Task;
import domain.TasksAppAPI;
import domain.enums.Job;
import domain.exceptions.TaskNotValid;
import domain.exceptions.TasksAppException;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class MockTasks2Consumer {
  public static void main(String[] args) throws TasksAppException, TaskNotValid {
    Proxy3270Emulator proxy = null;
    TasksAppAPI tasksApp = null;
    try {
      proxy = new ProxyWS3270("ws3270");
      MainframeAPI mainframe = new MusicAPI(proxy);
      tasksApp = new Tasks2API(proxy, mainframe);

      if (proxy.connect("155.210.71.101", "623").success()) {
        System.out.println("Connected!");

        mainframe.login("prog", "prog123");
        System.out.println("Login!");

        mainframe.executeJob(Job.TASKS2);
        System.out.println("Executing tasks2!");

        Task t =new Task(1800, "aaa", "a",
                          Calendar.getInstance(), 50, 50);
        tasksApp.addTask(t);

        //List<Task> t1 = tasksApp.listTasks();
        //List<Task> t2 = tasksApp.listTasks();

        //for (Task tas : t1) System.out.println(tas);
        //for (Task tas : t2) System.out.println(tas);

        tasksApp.exit();
        System.out.println("Exit tasks2!");
      }

      mainframe.logout();
      System.out.println("Logout!");

      if (proxy.disconnect().success()) System.out.println("Disconnect!");
    } catch (Exception e) {
      e.printStackTrace();

      Task t =new Task(1990, "aaa", "a",
              Calendar.getInstance(), 50, 50);

      tasksApp.addTask(t);
      List<Task> t1 = tasksApp.listTasks();
      List<Task> t2 = tasksApp.listTasks();

      for (Task tas : t1) System.out.println(tas);
      for (Task tas : t2) System.out.println(tas);

      if (proxy != null) {
        try {
          if (proxy.disconnect().success()) System.out.println("Disconnect!");
        } catch (Exception e2) {
          e2.printStackTrace();
        }
      }
    }
  }
}
