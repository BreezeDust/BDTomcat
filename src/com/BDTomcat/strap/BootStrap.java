package com.BDTomcat.strap;

import java.io.File;

import com.BDTomcat.Control.*;
import com.BDTomcat.Global.GlobalSet;
import com.BDTomcat.Global.MD5Util;
import com.BDTomcat.Global.initConfig;
public class BootStrap {
	public static void main(String[] args){
		initConfig.init();
		HttpConnector connector=new HttpConnector();
		connector.start();
	}
}
