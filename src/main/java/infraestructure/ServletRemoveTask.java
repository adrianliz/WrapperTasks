package infraestructure;

import domain.Proxy3270Emulator;
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
    name = "ServletRemoveTask",
    urlPatterns = {"/remove"})
public class ServletRemoveTask extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    Proxy3270Emulator proxy = (Proxy3270Emulator) session.getAttribute("proxy");
    TasksAppAPI tasksApp = (TasksAppAPI) session.getAttribute("tasksApp");

    int id = Integer.parseInt(request.getParameter("idTask"));
    String message;
    try {
      tasksApp.removeTask(id);
      message = "Task removed";
    } catch (TasksAppException e) {
      message = e.getErrorMessage().toString();
    }

    request.setAttribute("message", message);
    request.getRequestDispatcher("/WEB-INF/public/menu.jsp").forward(request, response);
  }
}
