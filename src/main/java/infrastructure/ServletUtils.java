/*
  ServletUtils.java
  09/01/2021
  @author Adrián Lizaga Isaac
 */
package infrastructure;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletUtils {
	protected static void dispatchUserNotLogged(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		request.setAttribute("errorMessage", "You are not logged in");
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
}
