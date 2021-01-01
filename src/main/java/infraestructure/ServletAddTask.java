package infraestructure;

import domain.Task;
import domain.TasksAppAPI;
import domain.exceptions.InvalidTask;
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
    name = "ServletAddTask",
    urlPatterns = {"/add"})
public class ServletAddTask extends HttpServlet {
  private static final int TASK_NAME_LENGTH = 16;
  private static final int TASK_DESCRIPTION_LENGTH = 32;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");
    HttpSession session = request.getSession(false);

    if (session != null) {
      TasksAppAPI tasksApp = (TasksAppAPI) session.getAttribute("tasksApp");

      if (tasksApp != null) {
        try {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          Date date = sdf.parse(request.getParameter("date"));
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(date);

          int idTask = Integer.parseInt(request.getParameter("idTask"));
          String name = request.getParameter("name");
          String description = request.getParameter("description");

          Task task =
              new Task(
                  idTask, name, description, calendar, TASK_NAME_LENGTH, TASK_DESCRIPTION_LENGTH);

          List<Task> tasks = (List<Task>) session.getAttribute("tasks");
          tasksApp.addTask(task);
          tasks.add(task);
          request.setAttribute("successMessage", "Task " + idTask + " added");
          session.setAttribute("disableBack", false);
        } catch (TasksAppException | InvalidTask | ParseException ex) {
          request.setAttribute("errorMessage", ex.getMessage());
          session.setAttribute("disableBack", true);
        }

        request.getRequestDispatcher("add.jsp").forward(request, response);
      } else {
        ServletUtils.dispatchUserNotLogged(request, response);
      }
    } else {
      ServletUtils.dispatchUserNotLogged(request, response);
    }
  }
}
