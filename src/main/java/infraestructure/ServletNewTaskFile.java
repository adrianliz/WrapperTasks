package infraestructure;

import domain.TasksAppAPI;
import domain.exceptions.TasksAppException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(
    name = "ServletNewTaskFile",
    urlPatterns = {"/new"})
public class ServletNewTaskFile extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession(false);

    if (session != null) {
      TasksAppAPI tasksApp = (TasksAppAPI) session.getAttribute("tasksApp");

      if (tasksApp != null) {
        try {
          tasksApp.newTaskFile();
          session.removeAttribute("tasks");
          request.setAttribute("successMessage", "New task file created");
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
