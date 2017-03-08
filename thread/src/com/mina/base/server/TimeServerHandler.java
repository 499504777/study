package com.mina.base.server;


import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class TimeServerHandler implements IoHandler {
	
	
	//当会话创建时被触发 
	public void sessionCreated(IoSession session) {  
		System.out.println(session.getRemoteAddress().toString()); 
	} 
	
	//当会话开始时被触发
	public void sessionOpened(IoSession session	){
		
	}
	
	//当会话关闭时被触发 
	public void sessionClosed(){
		
	}
	
	//当会话空闲时被触发 
	public void sessionIdle(){
		
	}
	
	// 当接口中其他方法抛出异常未被捕获时触发此方法
	public void exceptionCaught(){
		
	}
	
	//当发送消息后被触发 
	public void messageSent(){
		
	}
	
	
	//当接收到消息后被触发 
	@SuppressWarnings("deprecation")
	public void messageReceived( IoSession session, Object message ) throws Exception{
		String data = message.toString();
		if("quit".equals(data)){
			session.close();
			return;
		}
		session.write("Hello"); 
		session.close();
	}

	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		session.close();
		
	}

	@Override
	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
