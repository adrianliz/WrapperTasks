package infrastructure;

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
import java.util.List;

@WebServlet(
    name = "ServletRemoveTask",
    urlPatterns = {"/remove"})
public class ServletRemoveTask extends HttpServlet {

  private void removeTaskSession(HttpSession session, int idTask) {
    List<Task> tasks = (List<Task>) session.getAttribute("tasks");

    if (tasks != null) {
      tasks.removeIf(task -> Integer.parseInt(task.getId()) == idTask);
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession(false);

    if (session != null) {
      TasksAppAPI tasksApp = (TasksAppAPI) session.getAttribute("tasksApp");

      if (tasksApp != null) {
        int idTask = Integer.parseInt(request.getParameter("idTask"));

        try {
          tasksApp.removeTask(idTask);
          removeTaskSession(session, idTask);
          request.setAttribute("successMessage", "Task " + idTask + " removed");
        } catch (TasksAppException ex) {
          request.setAttribute("errorMessage", ex.getMessage());
        }

        request.getRequestDispatcher("menu.jsp").forward(request, response);
      } else {
        ServletUtils.dispatchUserNotLogged(request, response);
      }
    } else {
      ServletUtils.dispatchUserNotLogged(request, response);
    }
  }
}
