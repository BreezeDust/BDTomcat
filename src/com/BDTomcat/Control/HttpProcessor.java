package com.BDTomcat.Control;

public class HttpProcessor {
	private HttpConnector connector=null;
	private HttpRequest request=null;
	private HttpResponse response=null;
	public HttpProcessor(HttpConnector connector){
		this.connector=connector;
	}
	
}
