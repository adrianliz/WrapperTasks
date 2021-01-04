package infraestructure;

import domain.MainframeAPI;
import domain.Proxy3270Emulator;
import domain.TasksAppAPI;
import domain.enums.Job;
import domain.exceptions.TasksAppException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(
    name = "ServletExit",
    urlPatterns = {"/exit"})
public class ServletExit extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    HttpSession session = request.getSession(false);

    if (session != null) {
      Proxy3270Emulator emulator = (Proxy3270Emulator) session.getAttribute("emulator");
      MainframeAPI mainframe = (MainframeAPI) session.getAttribute("mainframe");
      TasksAppAPI tasksApp = (TasksAppAPI) session.getAttribute("tasksApp");

      if ((tasksApp != null) && (mainframe != null) && (emulator != null)) {
        try {
          tasksApp.exit();
          emulator.disconnect();
          mainframe.finishJob(Job.TASKS2);

          session.invalidate();
          request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (TasksAppException | IOException ex) {
          request.setAttribute("errorMessage", ex.getMessage());
          request.getRequestDispatcher("menu.jsp").forward(request, response);
        }
      } else {
        ServletUtils.dispatchUserNotLogged(request, response);
      }
    } else {
      ServletUtils.dispatchUserNotLogged(request, response);
    }
  }
}
