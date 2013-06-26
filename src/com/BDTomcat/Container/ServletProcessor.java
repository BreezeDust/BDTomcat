package com.BDTomcat.Container;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.BDTomcat.Control.HttpRequest;
import com.BDTomcat.Control.HttpResponse;
import com.BDTomcat.Global.GlobalSet;

public class ServletProcessor {
	private String servletName="";
	private String servletDir="";
	private HttpRequest request=null;
	private HttpResponse response=null;
	public void process(HttpRequest request, HttpResponse response){
		this.request=request;
		this.response=response;
		servletName=request.getFileName();
		String[] URLS=request.getRequestDirArr();
		for(int con=0;con<URLS.length-1;con++){
			if(!URLS[con].equals("")){
				servletDir+=URLS[con]+"\\";
			}
		}
		URLClassLoader loader = null;

        URL[] urls = new URL[1];
        URLStreamHandler streamHandler = null;
        File classPath = new File(GlobalSet.WEBROOT+"\\"+servletDir);
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
			servlet.service((ServletRequest) request, (ServletResponse) response);
			this.response.getDBwriter().superPush();
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
       

		
		
	} 
}
