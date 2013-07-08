package com.BDTomcat.Global;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.BDTomcat.Entity.ServletMap;

public class initConfig {
	public static void init(){
		System.out.println("----------->ServiceConfig...........");
		setServices();
	}
	public static boolean setServices(){
		DocumentBuilderFactory domfac=DocumentBuilderFactory.newInstance();
		DocumentBuilder dombuilder;
		try {
			dombuilder = domfac.newDocumentBuilder();
			InputStream cin=new FileInputStream(GlobalSet.WEBROOT+"\\service.xml");
			Document doc=dombuilder.parse(cin);
			NodeList set=doc.getElementsByTagName("ServerSocket");
			GlobalSet.port=Integer.parseInt(set.item(0).getAttributes().getNamedItem("port").getNodeValue());
			System.out.println("#set port : "+GlobalSet.port);
			
			set=doc.getElementsByTagName("ThreadPool");
			GlobalSet.minThread=Integer.parseInt(set.item(0).getAttributes().getNamedItem("min").getNodeValue());
			System.out.println("#set minThread : "+GlobalSet.minThread);
			
			GlobalSet.maxThread=Integer.parseInt(set.item(0).getAttributes().getNamedItem("max").getNodeValue());
			System.out.println("#set maxThread : "+GlobalSet.maxThread);
			set=doc.getElementsByTagName("Cache");
			String ruanCache=set.item(0).getAttributes().getNamedItem("page").getNodeValue();
			if(ruanCache.equals("true")){
				GlobalSet.ruanPageCache=true;
			}
			else{
				GlobalSet.ruanPageCache=false;
			}
			System.out.println("#run cache : "+GlobalSet.ruanPageCache);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;

	}
	
	public synchronized static boolean setSerletMap(String hostName){
		System.err.println("----------->initialization WebHost ："+hostName);
		File file=new File(GlobalSet.WEBROOT+"\\"+hostName+"\\WEB-INF\\web.xml");
		if(!file.exists()) return false;
		
		DocumentBuilderFactory domfac=DocumentBuilderFactory.newInstance();
		DocumentBuilder dombuilder;
		try {
			dombuilder = domfac.newDocumentBuilder();
			InputStream cin=new FileInputStream(GlobalSet.WEBROOT+"\\"+hostName+"\\WEB-INF\\web.xml");
			Document doc=dombuilder.parse(cin);
			NodeList set=doc.getElementsByTagName("servlet");
			for(int con=0;con<set.getLength();con++){
				String dir=set.item(con).getAttributes().getNamedItem("url").getNodeValue();
				String name=set.item(con).getAttributes().getNamedItem("name").getNodeValue();
				String packages=set.item(con).getAttributes().getNamedItem("class").getNodeValue();
				String packagesFile=packages.replace(".", File.separator);
				String sDir="";
				if(packagesFile.equals("")){
					sDir=GlobalSet.WEBROOT+"\\"+hostName+"\\WEB-INF\\classes\\"+name+".class";
				}
				else{
					sDir=GlobalSet.WEBROOT+"\\"+hostName+"\\WEB-INF\\classes\\"+packagesFile+"\\"+name+".class";
				}
				File tmpFile=new File(sDir);
				ServletMap servlets=new ServletMap("/"+hostName+dir,name,packages,tmpFile.lastModified());
				servlets.packagesFile=packagesFile;
				GlobalSet.servletMap.put("/"+hostName+dir, servlets);
				System.err.println("[set serlvet "+con+"] "+servlets.name+"   "+servlets.dir);

			}
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//初始化缓存
		File fileCache=new File(GlobalSet.WEBROOT+"\\"+hostName+"\\BDcache");
		if(!fileCache.exists()) fileCache.mkdir();
		GlobalSet.siteList.add(hostName);
		return true;
	}
	

}
