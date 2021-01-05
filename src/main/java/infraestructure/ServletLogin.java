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
  private static final String CONFIG = "/config.properties";
  private static final String S3270_PATH = "s3270_path";

  private String getS3270Path() throws IOException {
    Properties config = new Properties();
    config.load(this.getClass().getResourceAsStream(CONFIG));
    return config.getProperty(S3270_PATH);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    Proxy3270Emulator emulator = null;

    try {
      emulator = new ProxyS3270(getS3270Path());
      MainframeAPI mainframe = new MusicAPI(emulator);
      TasksAppAPI tasksApp = new Tasks2API(emulator, mainframe);
      emulator.connect(request.getParameter("IP"), request.getParameter("port"));
      mainframe.login(request.getParameter("user"), request.getParameter("password"));
      mainframe.executeJob(Job.TASKS2);

      HttpSession session = request.getSession(true);
      session.setAttribute("emulator", emulator);
      session.setAttribute("mainframe", mainframe);
      session.setAttribute("tasksApp", tasksApp);

      response.sendRedirect(request.getContextPath() + "/menu.jsp");
    } catch (AuthException | InvalidScreenException | IOException ex) {
      try {
        if (emulator != null) {
          emulator.disconnect();
        }

        request.setAttribute("errorMessage", ex.getMessage());
      } catch (IOException ex2) {
        request.setAttribute("errorMessage", ex2.getMessage());
      }

      request.getRequestDispatcher("index.jsp").forward(request, response);
    }
  }
}
