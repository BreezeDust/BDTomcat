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
import com.BDTomcat.Entity.ServletMap;
import com.BDTomcat.Global.GlobalSet;
import com.BDTomcat.Global.initConfig;

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
        	//获取输入输出流
			input = socket.getInputStream();
			output = socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //装载 request response
        request=new  HttpRequest(input);
        response=new HttpResponse(output);
        
        if(!request.isNORequest() && request.getRequestURI()!=null){
        	System.out.println("run"+request.getRequestURI());
        	if(request.getFileExp()==null){//Srvlet请求处理 
        		if(haveInited(request.getHostName()) && isTrueURI()){
                	ServletProcessor processor=new ServletProcessor();
                	processor.process(request, response);
                	
        		}
       		
        	}
        	else{//静态资源处理
            	StaticProcessor processor=new StaticProcessor();
    			processor.process(request, response);     		
        	}
        }
        


	}
	public synchronized boolean isTrueURI(){
		ServletMap app=(ServletMap)GlobalSet.servletMap.get(request.getRequestURI());
		if(app!=null) return true;
		return false;

	}
	public synchronized boolean haveInited(String Hostname){
		for(int con=0;con<GlobalSet.siteList.size();con++){
			if(GlobalSet.siteList.get(con).equals(Hostname)) return true;
		}
		if(initConfig.setSerletMap(Hostname)) return true;
		return false;
	} 
	@Override
	public void run() {
		while(!isStop){
			Socket socket=waitSocket(); //阻塞等待请求
			if(socket==null) continue;
			process(socket); //处理请求
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
