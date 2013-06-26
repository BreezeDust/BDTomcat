package com.BDTomcat.Global;

import java.io.File;

public class GlobalSet {
	//服务器根目录
	public static String WEBROOT=System.getProperty("user.dir") + File.separator  + "webroot";
	//请求端口
	public static int port=8888;
	//线程池最小线程数
	public static int minThread=2;
	//线程池最大线程数
	public static int maxThread=50;

}
