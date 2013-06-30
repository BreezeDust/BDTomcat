package com.BDTomcat.Entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import com.BDTomcat.Global.GlobalSet;

public class HttpBDSession implements HttpSession{
	private String sessionID=null;
	private HashMap valueMap=null;//保存用户session
	/***
	 * 不存在id时
	 */
	public HttpBDSession(){
		initSession();
	}
	private void initSession(){
		this.sessionID=createSessionID();
		valueMap=new HashMap();
		GlobalSet.sessionMap.put(this.sessionID, valueMap);	
	}
	/***
	 * 存在id时
	 * @param sessionID
	 */
	public HttpBDSession(String sessionID){
		this.sessionID=sessionID;
		valueMap=(HashMap) GlobalSet.sessionMap.get(sessionID);
	}
	/***
	 * 创建一个ID
	 * @return
	 */
	private String createSessionID(){
		Date date=new Date();
		SimpleDateFormat time=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return time.format(date).toString();
	} 
	@Override
	public Object getAttribute(String key) {
		// TODO Auto-generated method stub
		if(valueMap==null){
			initSession();
		}
		return valueMap.get(key);
	}

	@Override
	public Enumeration getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getCreationTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return sessionID;
	}

	@Override
	public long getLastAccessedTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxInactiveInterval() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSessionContext getSessionContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getValue(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getValueNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void invalidate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void putValue(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAttribute(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeValue(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAttribute(String key, Object value) {
		// TODO Auto-generated method stub
		if(valueMap==null){
			initSession();
		}
		valueMap.put(key, value);
		
		
	}

	@Override
	public void setMaxInactiveInterval(int arg0) {
		// TODO Auto-generated method stub
		
	}

}
