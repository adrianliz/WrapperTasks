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

  private HttpSession session;

  public ServletLogin() {
    try {
      proxy = new ProxyWS3270("ws3270");
      mainframe = new MusicAPI(proxy);
      tasksApp = new Tasks2API(proxy, mainframe);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String user = request.getParameter("user");
    String pwd = request.getParameter("password");
    String IP = request.getParameter("IP");
    String port = request.getParameter("port");

    //TODO: si ya esta conectado que no vuelva a hacerlo
    try {
      session = request.getSession(true);

      proxy.connect(IP, port);
      mainframe.login(user, pwd);
      mainframe.executeJob(Job.TASKS2);

      session.setAttribute("proxy", proxy);
      session.setAttribute("mainframe", mainframe);
      session.setAttribute("tasksApp", tasksApp);
      request.getRequestDispatcher("/WEB-INF/public/menu.jsp").forward(request, response);
    } catch (AuthException | InvalidScreenException ex) {
      ex.printStackTrace();
      proxy.disconnect();
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.getRequestDispatcher("/WEB-INF/public/index.jsp").forward(request, response);
  }
}
