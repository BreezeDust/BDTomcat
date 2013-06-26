package com.BDTomcat.Container;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.BDTomcat.Control.HttpRequest;
import com.BDTomcat.Control.HttpResponse;
import com.BDTomcat.Global.GlobalSet;

public class StaticProcessor {
	private static final int BUFFER_SIZE = 1024;
	private HttpRequest request=null;
	private HttpResponse response=null;
	public void process(HttpRequest request, HttpResponse response) {
		this.request=request;
		this.response=response;
		try {
			this.sendStaticResource();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	public void sendStaticResource() throws IOException {
	    byte[] bytes = new byte[BUFFER_SIZE];
	    FileInputStream fileStream = null;
	    try {
	      File file = new File(GlobalSet.WEBROOT, request.getRequestURI());
	     if (file.exists()) {
	    	  String fileExt=request.getFileExp().toLowerCase();
	    	  if(fileExt.equals("html") || fileExt.equals("css") || fileExt.equals("js")){
	    		  //文本
	    		  Scanner cin=new Scanner(file);
	    		  String strTXT="";
	    		  while(cin.hasNext()){
	    			  strTXT+=cin.nextLine()+"\r\n";
	    		  }
	    		  strTXT=getHeader(strTXT.getBytes().length,fileExt)+strTXT;
	    		  response.getOutput().write(strTXT.getBytes());
	    	  }
	    	  else{
	  	    	//文件  
	  	        fileStream = new FileInputStream(file);
	  	        int ch = fileStream.read(bytes, 0, BUFFER_SIZE);
	  	        while (ch!=-1) {
	  	        	response.getOutput().write(bytes, 0, ch);
	  	          ch = fileStream.read(bytes, 0, BUFFER_SIZE);
	  	        }	    		  
	    	  } 
	        
	      }
	      else {
	        String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
	          "Content-Type: text/html\r\n" +
	          "Content-Length: 23\r\n" +
	          "\r\n" +
	          "<h1>File Not Found</h1>";
	        response.getOutput().write(errorMessage.getBytes());
	      }
	    }
	    catch (Exception e) {
	      System.out.println(e.toString() );
	    }
	    finally {
	      if (fileStream!=null)
	        fileStream.close();
	    }
	  }
	public String getHeader(int length,String type){
		String state="";
		String stateStr="";
		if(type.equals("html")){
			type="text/html";
			state="200";
			stateStr="ok";
		} 
		if(type.equals("css")){
			type="text/css";
			state="200";
			stateStr="ok";
		} 
		if(type.equals("js")){
			type="text/javascript";
			state="200";
			stateStr="ok";
		} 
		Date date=new Date();
		SimpleDateFormat time=new SimpleDateFormat("EEE, dd MMMMM yyyy HH:mm:ss z");
		
		String header="HTTP/1.1 "+state+" "+stateStr+"\r\n"+
				      "Server: BDTomcat/1.0\r\n"+
				      "Content-Type: "+type+"\r\n"+
				      "Content-length: "+length+"\r\n"+
				      "Date: "+time.format(date)+"\r\n"+
				      "\r\n";
		return header;
	}
	 
}
