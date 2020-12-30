package infraestructure;

import application.MusicAPI;
import domain.MainframeAPI;
import domain.Proxy3270Emulator;
import domain.TasksAppAPI;
import domain.exceptions.AuthException;
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
    Proxy3270Emulator proxy = (Proxy3270Emulator) session.getAttribute("proxy");
    MainframeAPI mainframe = (MainframeAPI) session.getAttribute("mainframe");
    TasksAppAPI tasksApp = (TasksAppAPI) session.getAttribute("tasksApp");

    try {
      tasksApp.exit();
      mainframe.logout();
    } catch (TasksAppException | AuthException e) {
      e.printStackTrace();
    }

    proxy.disconnect();
    session.invalidate();
    response.sendRedirect("login");
  }
}
