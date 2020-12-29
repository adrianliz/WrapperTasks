package infraestructure;

import application.MusicAPI;
import application.ProxyWS3270;
import application.Tasks2API;
import domain.MainframeAPI;
import domain.Proxy3270Emulator;
import domain.Task;
import domain.TasksAppAPI;
import domain.enums.Job;

import java.time.LocalDate;
import java.util.Calendar;

public class MockTasks2Consumer {
  public static void main(String[] args) {
    Proxy3270Emulator proxy = null;
    try {
      proxy = new ProxyWS3270("ws3270");
      MainframeAPI mainframe = new MusicAPI(proxy);
      TasksAppAPI tasksApp = new Tasks2API(proxy, mainframe);

      if (proxy.connect("155.210.71.101", "623").success()) {
        System.out.println("Connected!");

        mainframe.login("prog", "prog123");
        System.out.println("Login!");

        mainframe.executeJob(Job.TASKS2);
        System.out.println("Executing tasks2!");

        for (Task t : tasksApp.listTasks()) {
          System.out.println(t);
        }
        System.out.println("Tasks listed!");

        Calendar testDate = Calendar.getInstance();
        testDate.set(1989, Calendar.SEPTEMBER, 9, 0, 0);

        for (Task t: tasksApp.searchTasks(testDate)) {
           System.out.println(t);
        }
        System.out.println("Tasks searched!");

        tasksApp.exit();
        System.out.println("Exit tasks2!");
      }

      mainframe.logout();
      System.out.println("Logout!");

      if (proxy.disconnect().success()) System.out.println("Disconnect!");
    } catch (Exception e) {
      e.printStackTrace();

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
