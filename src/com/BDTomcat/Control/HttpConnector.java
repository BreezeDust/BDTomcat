package com.BDTomcat.Control;

import java.io.IOException;
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
			HttpProcessor processer=new HttpProcessor(this);
			threadList.offer(processer);
		}
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
		while(running){
			try {
				Socket socket = serverSocket.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int con=0;con<100;con++){
				System.out.println("a"+con);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public void start(){
		Thread thread=new Thread(this);
		thread.start();
	}

}
