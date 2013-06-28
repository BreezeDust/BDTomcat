import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class b extends  HttpServlet{
	public void service(HttpServletRequest request,HttpServletResponse response) throws IOException{
		System.out.println("Running bServlet");
	    PrintWriter out = response.getWriter(); 
	    Cookie[] cookies=request.getCookies();
	    for(int con=0;con<cookies.length;con++){
	    	out.println(cookies[con].getName()+" "+cookies[con].getValue());
	    }
	    out.println("cookies");
	    out.println("<br/>");
	    HttpSession session=request.getSession();
	    out.println(session.getAttribute("age").toString());
	    out.println("SESSION");
	}

}
