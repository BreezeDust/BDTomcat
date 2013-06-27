package com.BDTomcat.Control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

import com.BDTomcat.Global.*;

public class HttpConnector implements Runnable{
	public boolean running=true;
	private Queue<HttpProcessor> threadList=new LinkedList();
	public HttpConnector(){
		this.initThreadList();
	}
	/****
	 * 初始化线程池
	 */
	public void initThreadList(){
		int cons=0;
		if(GlobalSet.minThread<GlobalSet.maxThread){
			cons=GlobalSet.maxThread;
		}
		else{
			cons=GlobalSet.minThread;
		}
		for(int con=0;con<cons;con++){
			HttpProcessor processor=new HttpProcessor(this);
			processor.start();
			processor.setThreadID(con);
			threadList.offer(processor);
		}
		System.out.println("ThreadPool is ready!!");
	}
	public void recycle(HttpProcessor processor){
		threadList.offer(processor);
	}
	/***
	 * 从线程池中返回一个HttpProcessor
	 * @return
	 */
	public HttpProcessor creatHttpProcessor(){
		if(threadList.size()>0){
			 return threadList.poll();
		}
		return null;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ServerSocket serverSocket = null;
		try {
			serverSocket =  new ServerSocket(GlobalSet.port, 1, InetAddress.getByName("127.0.0.1"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int con=1;
		while(running){
			try {

				Socket socket = serverSocket.accept();
				HttpProcessor processor=creatHttpProcessor();
				
				if(processor==null){
					System.out.println("out Tread!!!");
				}
				else{
					System.out.println("======="+(con++)+"  T:"+processor.getThreadID());
					processor.assign(socket);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	public void start(){
		Thread thread=new Thread(this);
		thread.start();
	}

}
