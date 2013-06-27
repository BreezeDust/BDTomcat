package com.BDTomcat.Control;


import java.io.OutputStream;
import java.io.PrintWriter;

public class BDPrintWriter extends PrintWriter {
	private  String cacheStr="";
	
	public BDPrintWriter(OutputStream writer) {
		super(writer,true);
	}
	public void superPush(){
		super.print(cacheStr);
		super.close();
	}
	
	public void println(String s) {
		cacheStr+=s+"\r\n";
	}
	public void print(String s){
		cacheStr+=s;
	}
	
	
}
