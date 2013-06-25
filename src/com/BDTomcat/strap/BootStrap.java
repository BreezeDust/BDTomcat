package com.BDTomcat.strap;

import com.BDTomcat.Control.*;
public class BootStrap {
	public static void main(String[] args){
		HttpConnector connector=new HttpConnector();
		connector.start();
		for(int con=0;con<100;con++){
			System.out.println("main"+con);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
