/*
  ServletSaveTasks.java
  09/01/2021
  @author Borja Rando Jarque
 */
package infrastructure;

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
    name = "ServletSaveTasks",
    urlPatterns = {"/save"})
public class ServletSaveTasks extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession(false);

    if (session != null) {
      TasksAppAPI tasksApp = (TasksAppAPI) session.getAttribute("tasksApp");

      if (tasksApp != null) {
        try {
          tasksApp.saveTasks();
          request.setAttribute("successMessage", "Tasks saved");
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
