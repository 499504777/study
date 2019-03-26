package com.mina.base.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class TimeClientHandler extends IoHandlerAdapter {
	Object message;
	public void messageReceived(IoSession session, Object message) throws Exception { 
		this.message = message;//显示接收到的消息 
	}
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	} 
	
	
	
	
}
