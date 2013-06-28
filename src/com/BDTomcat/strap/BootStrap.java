package com.BDTomcat.strap;

import com.BDTomcat.Control.*;
import com.BDTomcat.Global.initConfig;
public class BootStrap {
	public static void main(String[] args){
		initConfig.init();
		HttpConnector connector=new HttpConnector();
		connector.start();
	}
}
