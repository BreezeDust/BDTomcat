package com.BDTomcat.Global;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GlobalSet {
	//服务器根目录
	public static String WEBROOT=System.getProperty("user.dir") + File.separator  + "webroot";
	//请求端口
	public static int port=0;
	//线程池最小线程数
	public static int minThread=0;
	//线程池最大线程数
	public static int maxThread=0;
	//SESSION 并发查询HASH表
	public static Map sessionMap=new ConcurrentHashMap();
	//Servlet 并发查询HASH表
	public static Map servletMap=new ConcurrentHashMap();
	//已经初始化的站点列表
	public  static List<String> siteList=new LinkedList(); 

}
