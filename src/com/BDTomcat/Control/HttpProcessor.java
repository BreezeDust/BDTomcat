package com.BDTomcat.Control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.BDTomcat.Container.ServletProcessor;
import com.BDTomcat.Container.StaticProcessor;
import com.BDTomcat.Entity.HttpRequest;
import com.BDTomcat.Entity.HttpResponse;

public class HttpProcessor implements Runnable{
	private int threadID=0;
	private HttpConnector connector=null;
	private HttpRequest request=null;
	private HttpResponse response=null;
	private boolean isAssign=false;
	private Socket socket=null;
	private boolean isStop=false;
	public HttpProcessor(HttpConnector connector){
		this.connector=connector;
	}
	public int getThreadID() {
		return threadID;
	}
	public void setThreadID(int threadID) {
		this.threadID = threadID;
	}
	public void start(){
		Thread thread=new Thread(this);
		thread.start();
	}
	public synchronized void assign(Socket socket){
		isAssign=true;
		this.socket=socket;
		this.notifyAll();
		
	}
	public synchronized Socket waitSocket(){
		while(!isAssign){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		isAssign=false;
		return socket;
	}
	private void process(Socket socket){
		InputStream input = null;
	    OutputStream output = null;
        try {
			input = socket.getInputStream();
			output = socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        request=new  HttpRequest(input);
        response=new HttpResponse(output);
        
        if(!request.isNORequest() && request.getRequestURI()!=null){
        	System.out.println("run"+request.getRequestURI());
        	if(request.getFileExp()==null){
            	ServletProcessor processor=new ServletProcessor();
            	processor.process(request, response);       		
        	}
        	else{
            	StaticProcessor processor=new StaticProcessor();
    			processor.process(request, response);     		
        	}
        }
        


	}
	@Override
	public void run() {
		while(!isStop){
			Socket socket=waitSocket(); //阻塞等待请求
			if(socket==null) continue;
			process(socket);
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connector.recycle(this); //释放线程给线程池
		}

		
	}
	
}
