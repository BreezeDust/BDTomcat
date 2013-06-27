package com.BDTomcat.strap;

import com.BDTomcat.Control.*;
public class BootStrap {
	public static void main(String[] args){
		System.out.println(new HttpBDSession().getId());
		HttpConnector connector=new HttpConnector();
		connector.start();
	}
}
