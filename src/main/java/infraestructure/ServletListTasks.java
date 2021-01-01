package infraestructure;

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
  private HttpSession session;
  private TasksAppAPI tasksApp;

  private boolean initialize(HttpServletRequest request) {
    session = request.getSession(false);

    if (session != null) {
      tasksApp = (TasksAppAPI) session.getAttribute("tasksApp");
    }

    return tasksApp != null;
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    if (initialize(request)) {
      try {
        List<Task> tasks = tasksApp.listTasks();
        session.setAttribute("tasks", tasks);
      } catch (TasksAppException ex) {
        request.setAttribute("errorMessage", ex.getMessage());
      }

      request.getRequestDispatcher("menu.jsp").forward(request, response);
    } else {
      ServletUtils.dispatchUserNotLogged(request, response);
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    if (initialize(request)) {
      try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(request.getParameter("date"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        List<Task> tasks = tasksApp.searchTasks(calendar);
        session.setAttribute("tasks", tasks);
      } catch (TasksAppException | ParseException ex) {
        request.setAttribute("errorMessage", ex.getMessage());
      }

      request.getRequestDispatcher("menu.jsp").forward(request, response);
    } else {
      ServletUtils.dispatchUserNotLogged(request, response);
    }
  }
}
