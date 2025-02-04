/*
  CharsetFilter.java
  09/01/2021
  @author Adrián Lizaga Isaac
 */
package infrastructure;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {
  private String encoding;

  public void init(FilterConfig config) {
    encoding = config.getInitParameter("requestEncoding");
    if (encoding == null) encoding = "UTF-8";
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
      throws IOException, ServletException {

    if (null == request.getCharacterEncoding()) {
      request.setCharacterEncoding(encoding);
    }

    response.setContentType("text/html; charset=UTF-8");
    response.setCharacterEncoding("UTF-8");

    next.doFilter(request, response);
  }
}
