import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





public class test extends  HttpServlet {
	public void service(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
	    System.out.println("Running testServlet");
	    PrintWriter out = response.getWriter(); 
	    out.println("<html>");
	    out.println("<head>");
	    out.println("<link href='css//test.css' rel='stylesheet' //>");
	    out.println("</head>");
	    out.println("<body>");
	    out.println("<h1>"+request.getParameter("tag")+"</h1>");
	    out.println("<h1>"+request.getParameter("bb")+"</h1>");
	    out.println("</body>");
	    out.println("</html>");
	    Cookie[] cookies=request.getCookies();
			
	}
}

