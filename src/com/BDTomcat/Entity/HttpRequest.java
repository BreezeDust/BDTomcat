package com.BDTomcat.Entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class HttpRequest implements HttpServletRequest{
	private InputStream input;
	private StringBuffer request=new StringBuffer(2048);
	private String requestURI="";
	private String[] requestDirArr=null;//URL分割数组
	private String fileExp=null;
	private String queryString=null; //get POST参数字符串
	private String[] parameterS=null;; //参数
	private String fileName=null;
	private String cookieStr=null; //cookieStr数据块
	private Cookie[] COOKIES=null;
	private String URInoQuering=null;
	private String hostName=null;
	private HttpBDSession session=null; 
	private boolean isNORequest=false; //是否由请求
	private List<String> lineList=new LinkedList(); //行分割
	
	public boolean isNORequest() {
		return isNORequest;
	}

	
	private String method=null;
	public String getFileName() {
		return fileName;
	}
	public void setRequestURI(String requestURI) {
		requestDirArr=requestURI.split("/");
	    String[] tmpStrs= requestDirArr[requestDirArr.length-1].split("[.]");
	    if(tmpStrs.length>1){
	    	fileExp=tmpStrs[1];
	    	
	    }
	    else{
	    	fileExp=null;
	    }
	    fileName=tmpStrs[0];
		this.requestURI = requestURI;
	}
	public String getFileExp() {
		return fileExp;
	}
	public String getURInoQuering() {
		return URInoQuering;
	}
	public String getHostName() {
		return hostName;
	}
	public String[] getRequestDirArr() {
		return requestDirArr;
	}

	byte[] buffer = new byte[2048];

	  public HttpRequest(InputStream input) {
	    this.input = input;
	    this.init();
	
	  }
	/***
	 * 将请求写入缓冲区
	 */
	private void init(){
		//将请求写入缓冲区
	    int con;
	    try {
	      con = input.read(buffer);
	    }
	    catch (IOException e) {
	      e.printStackTrace();
	      con = -1;
	    }
	    for (int con1=0; con1<con; con1++) {
	      request.append((char) buffer[con1]);
	    }
	    getHTTPLine();//分割每行
	    //System.out.println(request.toString());
	    //初始 化URI
	    if(request.equals("")){
	    	isNORequest=true;
	    }
	    else{
		    getRequestURIInit();
		    if(requestURI!=null && !requestURI.equals("")){
		    	//根据?#把请求分割
		    	String str=new String(requestURI);
		    	int index1,index2;
		    	index1 = str.indexOf('?');
		    	index2=str.indexOf('#');
		    	if(index2>0){
		    		str=str.substring(0,index2);
		    	}
		    	URInoQuering=new String(str);
		    	if(index1>0){
		    		//分割出get请求
		    		queryString=str.substring(index1+1,str.length());
		    		str=str.substring(0,index1);
		    		URInoQuering=new String(str);
		    	}    	
			    requestDirArr=str.split("/");
			    if(requestDirArr.length>1 && requestDirArr[0].equals("")) hostName=requestDirArr[1];
			    else hostName=requestDirArr[0];
			    
			    //得到扩展名
			    String[] tmpStrs= requestDirArr[requestDirArr.length-1].split("[.]");
			    if(tmpStrs.length>1){
			    	fileExp=tmpStrs[1];
			    	
			    }
			    else{
			    	fileExp=null;
			    }
			    fileName=tmpStrs[0];
			    
		    }	    	
	    }


	}
	public void getHTTPLine(){
		String[] lineLists=request.toString().split("[\r\n]");
		for(int con=0;con<lineLists.length;con++){
			if(!lineLists[con].equals("")){
				lineList.add(lineLists[con]);
				//初始化COOKIE和SESSION
				if(lineLists[con].matches("Cookie.*")) initCookieAndSession(lineLists[con]);
			}
		}
	}
	/***
	 * 提取cookie和sessionid并初始化session
	 * @param cookieStr
	 */
	public void initCookieAndSession(String cookieStr) {
		Cookie[] cookies = null;
		String str = cookieStr.replaceAll("Cookie:", "");
		str = str.trim();
		String[] cookieStrTmp = str.split("; ");
		cookies = new Cookie[cookieStrTmp.length];
		for (int con1 = 0; con1 < cookieStrTmp.length; con1++) {
			String[] name_value = cookieStrTmp[con1].split("=");
			cookies[con1] = new Cookie(name_value[0].trim(),
					name_value[1].trim());
			if (name_value[0].trim().toLowerCase().equals("jsessionid")) {
				session = new HttpBDSession(name_value[1].trim());
			}
		}
		COOKIES = cookies;

	}
	@Override
	public Object getAttribute(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getContentLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration getLocales() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getParameter(String arg0) {
		// TODO Auto-generated method stub
		String tmpStr=getQueryString();
		if(tmpStr==null) return null;
		if(parameterS==null){
			parameterS=tmpStr.split("[&]");
		}
		for(int con=0;con<parameterS.length;con++){
			if(!parameterS[con].equals("")){
				if(parameterS[con].matches(".*"+arg0+".*")){
					return parameterS[con].split("[=]")[1].trim();
				}
			}
		}
		return null;
	}

	@Override
	public Map getParameterMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration getParameterNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getParameterValues(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProtocol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRealPath(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getScheme() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServerName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getServerPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeAttribute(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAttribute(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterEncoding(String arg0)
			throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAuthType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContextPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cookie[] getCookies() {
		// TODO Auto-generated method stub

		
		return COOKIES;
	}

	@Override
	public long getDateHeader(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getHeader(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration getHeaderNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration getHeaders(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIntHeader(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getMethod() {
		// TODO Auto-generated method stub
		return method;
	}

	@Override
	public String getPathInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPathTranslated() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQueryString() {
		if(method.toLowerCase().equals("post")){
			String str=lineList.get(lineList.size()-1);
			if(str.matches(".*=.*")){
				queryString=str+"&"+queryString;
			}

		}
		return queryString;
	}

	@Override
	public String getRemoteUser() {
		// TODO Auto-generated method stub
		return null;
	}
	public void getRequestURIInit(){
		if(lineList.size()>0){
			String[] str=lineList.get(0).split(" ");
			method=str[0];
			requestURI=str[1];
		}
		else{
			method="get";
			requestURI=null;
		}


	}
	@Override
	public String getRequestURI() {
		return requestURI;
	}

	@Override
	public StringBuffer getRequestURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRequestedSessionId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSession getSession() {
		if(session==null) session=new HttpBDSession();
		return session;
	}

	@Override
	public HttpSession getSession(boolean arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Principal getUserPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromUrl() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUserInRole(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
