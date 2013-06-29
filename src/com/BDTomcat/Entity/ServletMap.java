package com.BDTomcat.Entity;

public class ServletMap {
	public String dir="";
	public String name="";
	public String packages="";
	public String packagesFile;
	public long lastTime;
	
	public ServletMap(String dir,String name,String packages,long lastTime){
		this.dir=dir;
		this.name=name;
		this.packages=packages;
		this.lastTime=lastTime;
	}
}
