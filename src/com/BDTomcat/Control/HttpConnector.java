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
		System.out.println("----------->initialization ThreadPool ： "+GlobalSet.maxThread);
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
		System.err.println("[Welcom to BDTomcat v1.0]");
	}
	/***
	 * 回收线程
	 * @param processor
	 */
	
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
			serverSocket =  new ServerSocket(GlobalSet.port);
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
				//从线程池中取出一个可用线程
				HttpProcessor processor=creatHttpProcessor();
				
				if(processor==null){
					System.out.println("out Tread!!!");
				}
				else{
					//System.out.println("======="+(con++)+"  T:"+processor.getThreadID());
					//分配任务
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
