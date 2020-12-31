package infraestructure;

import application.MusicAPI;
import application.ProxyWS3270;
import application.Tasks2API;
import domain.MainframeAPI;
import domain.Proxy3270Emulator;
import domain.TasksAppAPI;
import domain.enums.Job;
import domain.exceptions.AuthException;
import domain.exceptions.InvalidScreenException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(
    name = "login",
    urlPatterns = {"/login"})
public class ServletLogin extends HttpServlet {
  private Proxy3270Emulator proxy;
  private MainframeAPI mainframe;
  private TasksAppAPI tasksApp;

  @Override
  public void init() throws ServletException {
    super.init();

    try {
      proxy = new ProxyWS3270("ws3270");
      mainframe = new MusicAPI(proxy);
      tasksApp = new Tasks2API(proxy, mainframe);
    } catch (IOException ex) {
      System.err.println(ex.getMessage());
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {
      String user = request.getParameter("user");
      String pwd = request.getParameter("password");
      String IP = request.getParameter("IP");
      String port = request.getParameter("port");

      proxy.connect(IP, port);
      mainframe.login(user, pwd);
      mainframe.executeJob(Job.TASKS2);

      HttpSession session = request.getSession(true);
      session.setAttribute("proxy", proxy);
      session.setAttribute("mainframe", mainframe);
      session.setAttribute("tasksApp", tasksApp);

      request.getRequestDispatcher("menu.jsp").forward(request, response);
    } catch (AuthException | InvalidScreenException | IOException ex) {
      try {
        proxy.disconnect();
        request.setAttribute("errorMessage", ex.getMessage());
      } catch (IOException ex2) {
        request.setAttribute("errorMessage", ex2.getMessage());
      }

      request.getRequestDispatcher("index.jsp").forward(request, response);
    }
  }
}
