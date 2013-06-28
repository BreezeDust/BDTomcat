package com.BDTomcat.Entity;


import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class BDPrintWriter extends PrintWriter {
	private  String cacheStr="";
	private int length;
	private HttpRequest request=null;
	private HttpResponse response=null;
	public BDPrintWriter(OutputStream writer) {

		super(writer,true);
	}
	public String getHeader(){
		Date date=new Date();
		SimpleDateFormat time=new SimpleDateFormat("EEE, dd MMMMM yyyy HH:mm:ss z");
		
		String header="HTTP/1.1 200 ok\r\n"+
				      "Server: BDTomcat/1.0\r\n"+
				      "Content-Type: text/html\r\n"+
				      "Content-length: "+length+"\r\n"+
				      "Date: "+time.format(date)+"\r\n"+
				      "Set-Cookie: jsessionid="+request.getSession().getId()+"; path=/\r\n";
		List<String> list=response.getAddCookieList();
		for(int con=0;con<list.size();con++){
			header+="Set-Cookie: "+list.get(con)+"; path=/\r\n";
		}
		header+="\r\n";
		return header;		
	}
	public void superPush(HttpRequest request,HttpResponse response){
		this.request=request;
		this.response=response;
		length=cacheStr.getBytes().length;
		cacheStr=getHeader()+cacheStr;
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
