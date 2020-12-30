package infraestructure;

import domain.MainframeAPI;
import domain.Proxy3270Emulator;
import domain.Task;
import domain.TasksAppAPI;
import domain.exceptions.TasksAppException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@WebServlet(
    name = "ServletListTasks",
    urlPatterns = {"/list"})
public class ServletListTasks extends HttpServlet {
  private TasksAppAPI tasksApp;
  private Proxy3270Emulator proxy;

  private void initialize(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    proxy = (Proxy3270Emulator) session.getAttribute("proxy");
    tasksApp = (TasksAppAPI) session.getAttribute("tasksApp");
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      initialize(request);

      List<Task> tasks = tasksApp.listTasks();
      request.setAttribute("tasks", tasks);
      request.getRequestDispatcher("/WEB-INF/public/menu.jsp").forward(request, response);
    } catch (TasksAppException e) {
      e.printStackTrace();
      proxy.disconnect();
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      initialize(request);

      Date date = sdf.parse(request.getParameter("date"));
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);

      List<Task> tasks = tasksApp.searchTasks(calendar);
      request.setAttribute("tasks", tasks);
      request.getRequestDispatcher("/WEB-INF/public/menu.jsp").forward(request, response);
    } catch (TasksAppException | ParseException e) {
      e.printStackTrace();
    }
  }
}
