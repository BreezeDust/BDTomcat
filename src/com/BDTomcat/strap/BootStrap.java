package com.BDTomcat.strap;

import com.BDTomcat.Control.*;
public class BootStrap {
	public static void main(String[] args){
		HttpConnector connector=new HttpConnector();
		connector.start();
	}
}
