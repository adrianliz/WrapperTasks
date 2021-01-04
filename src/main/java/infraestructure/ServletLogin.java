package infraestructure;

import application.MusicAPI;
import application.ProxyS3270;
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
import java.util.Properties;

@WebServlet(
    name = "login",
    urlPatterns = {"/login"})
public class ServletLogin extends HttpServlet {
  private final static String CONFIG = "/config.properties";
  private final static String S3270_PATH = "s3270_path";

  private Proxy3270Emulator proxy;
  private MainframeAPI mainframe;
  private TasksAppAPI tasksApp;

  private String initError;

  private String getS3270Path() throws IOException {
    Properties config = new Properties();
    config.load(this.getClass().getResourceAsStream(CONFIG));
    return config.getProperty(S3270_PATH);
  }

  @Override
  public void init() throws ServletException {
    super.init();

    try {
      proxy = new ProxyS3270(getS3270Path());
      mainframe = new MusicAPI(proxy);
      tasksApp = new Tasks2API(proxy, mainframe);
    } catch (IOException ex) {
      initError = ex.getMessage();
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {

    if (initError != null) {
      request.setAttribute("errorMessage", initError);
      request.getRequestDispatcher("index.jsp").forward(request, response);
    } else {
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

        response.sendRedirect(request.getContextPath() + "/menu.jsp");
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
}
