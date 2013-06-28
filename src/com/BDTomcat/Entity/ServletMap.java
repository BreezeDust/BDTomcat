package com.BDTomcat.Entity;

public class ServletMap {
	public String dir="";
	public String name="";
	public String packages="";
	public String md5="";
	
	public ServletMap(String dir,String name,String packages,String md5){
		this.dir=dir;
		this.name=name;
		this.packages=packages;
		this.md5=md5;
	}
}
