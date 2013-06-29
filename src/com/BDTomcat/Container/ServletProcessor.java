package com.BDTomcat.Container;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.BDTomcat.Entity.HttpRequest;
import com.BDTomcat.Entity.HttpResponse;
import com.BDTomcat.Global.CacheManage;
import com.BDTomcat.Global.GlobalSet;
import com.BDTomcat.Global.MD5Util;

public class ServletProcessor {
	private String servletName="";
	private HttpRequest request=null;
	private HttpResponse response=null;
	
	public void process(HttpRequest request, HttpResponse response){
		this.request=request;
		this.response=response;
		this.sendServlet();
	}
	/***
	 * 处理Servlet
	 */
	public void sendServlet(){
		servletName=request.getServletPage()+"."+request.getFileName();
		//创建Srvlet加载器 
		URLClassLoader loader = null;

        URL[] urls = new URL[1];
        URLStreamHandler streamHandler = null;
        //得到Servlet目录
        File classPath = new File(GlobalSet.WEBROOT+"\\"+request.getHostName()+"\\WEB-INF\\classes");

        String repository;
        
		try {
			repository = (new URL("file", null, classPath.getCanonicalPath()
					+ File.separator)).toString();
			urls[0] = new URL(null, repository, streamHandler);
			loader = new URLClassLoader(urls);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Servlet servlet=null; 
		try {
			//加载Servlet
			servlet=(Servlet)loader.loadClass(servletName).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			servlet.service(request, response);
			this.response.getDBwriter().superPush(request, response);
			
			servlet.destroy();
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}

}
