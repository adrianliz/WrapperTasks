package infraestructure;

import domain.Proxy3270Emulator;
import domain.Task;
import domain.TasksAppAPI;
import domain.exceptions.TaskNotValid;
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

@WebServlet(
    name = "ServletAddTask",
    urlPatterns = {"/add"})
public class ServletAddTask extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");

    HttpSession session = request.getSession(false);
    Proxy3270Emulator proxy = (Proxy3270Emulator) session.getAttribute("proxy");
    TasksAppAPI tasksApp = (TasksAppAPI) session.getAttribute("tasksApp");

    String message;
    try {
      int id = Integer.parseInt(request.getParameter("idTask"));
      String name = request.getParameter("name");
      String description = request.getParameter("description");
      System.out.println(description);

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date date = sdf.parse(request.getParameter("date"));
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);

      Task task = new Task(id, name, description, calendar, 16, 32);
      tasksApp.addTask(task);

      message = "Task added";
    } catch (ParseException | TaskNotValid | TasksAppException e) {
      message = e.getMessage();
    }

    request.setAttribute("message", message);
    request.getRequestDispatcher("/WEB-INF/public/menu.jsp").forward(request, response);
  }
}
