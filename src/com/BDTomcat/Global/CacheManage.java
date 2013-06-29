package com.BDTomcat.Global;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.BDTomcat.Entity.HttpRequest;
import com.BDTomcat.Entity.HttpResponse;
import com.BDTomcat.Entity.ServletMap;

public class CacheManage {
	/***
	 * 检查是否有缓存
	 * @param hostName
	 * @return
	 */
	public synchronized static String haveCache(String hostName,HttpRequest request){
		if(!GlobalSet.ruanPageCache)return "";
		String cacheName=MD5Util.MD5(request.getRequestURI()+"?"+request.getQueryString()); 
		String dir=GlobalSet.WEBROOT+"\\"+hostName+"\\BDcache\\"+cacheName+".html";
		File file=new File(dir);
		if(file.exists()){
			dir="/"+hostName+"/BDcache/"+cacheName+".html";
			return dir;
		}
		return "";
	}
	/***
	 * 清除过期缓存
	 * @param hostName
	 */
	public  synchronized static void clearCache(String hostName){
		deleteFile(GlobalSet.WEBROOT+"\\"+hostName+"\\BDcache\\");
	}
	/***
	 * 删除指定目录的所有文件
	 * @param filePath
	 * @return
	 */
	private synchronized static boolean deleteFile(String filePath){
		File file = new File(filePath);
		File[] files = file.listFiles();
		for(File deleteFile : files){
			if(!deleteFile.delete()){
				//如果失败则返回
				return false;
			}
		}
		return true;
	}
	/***
	 *写入缓存  
	 */
	public synchronized static void writeCache(HttpRequest request, HttpResponse response){
		String cacheName=MD5Util.MD5(request.getRequestURI()+"?"+request.getQueryString());
		try {
			PrintWriter cout=new PrintWriter(new File(GlobalSet.WEBROOT+"\\"+request.getHostName()+"\\BDcache\\"+cacheName+".html"));
			cout.print(response.getDBwriter().getWebCache());
			cout.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/***
	 * 文件是否更新
	 * @return
	 */
	public  synchronized static boolean isFileTime(String hostName,ServletMap app){
		String packagesFile;
		if(app.packages.equals("")){
			packagesFile=GlobalSet.WEBROOT+"\\"+hostName+"\\WEB-INF\\classes\\"+app.name+".class";
		}
		else{
			packagesFile=GlobalSet.WEBROOT+"\\"+hostName+"\\WEB-INF\\classes\\"+app.packagesFile+"\\"+app.name+".class";
		}
		File file=new File(packagesFile);
		if(file.lastModified()==app.lastTime) return true;
		app.lastTime=file.lastModified();
		return false;	
	}
}
