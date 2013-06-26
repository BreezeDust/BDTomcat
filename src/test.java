

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class test implements Servlet {

  public void init(ServletConfig config) throws ServletException {

  }

  public void service(ServletRequest request, ServletResponse response)
    throws ServletException, IOException {
    System.out.println("Running testServlet");
    PrintWriter out = response.getWriter(); 
    out.println("<html>");
    out.println("<head>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>Hello BDTomcat</h1>");
    out.println("</body>");
    out.println("</html>");
 
  }
  public void destroy() {
    System.out.println("testServlet destroy");
  }

  public String getServletInfo() {
    return null;
  }
  public ServletConfig getServletConfig() {
    return null;
  }

}

