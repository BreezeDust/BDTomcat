package com.BDTomcat.Control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
	    FileInputStream fis = null;
	    try {
	      File file = new File(GlobalSet.WEBROOT, request.getRequestURI());
	      if (file.exists()) {
	        fis = new FileInputStream(file);
	        int ch = fis.read(bytes, 0, BUFFER_SIZE);
	        while (ch!=-1) {
	        	response.getOutput().write(bytes, 0, ch);
	          ch = fis.read(bytes, 0, BUFFER_SIZE);
	        }
	      }
	      else {
	        // file not found
	        String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
	          "Content-Type: text/html\r\n" +
	          "Content-Length: 23\r\n" +
	          "\r\n" +
	          "<h1>File Not Found</h1>";
	        response.getOutput().write(errorMessage.getBytes());
	      }
	    }
	    catch (Exception e) {
	      // thrown if cannot instantiate a File object
	      System.out.println(e.toString() );
	    }
	    finally {
	      if (fis!=null)
	        fis.close();
	    }
	  }
}
