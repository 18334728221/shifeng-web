package com.xwd.websocket.service;

/**
 * websocket发送接口
 * @author ljl
 *
 */
public interface WebsocketService {

	/**
	 * 根据消息类型发送
	 * @param msgType
	 * @param msg 一般是JSON串
	 */
	public void send(String msgType, String msg);
	
	/**
	 * 群发
	 * @param msg 一般是JSON串
	 */
	public void send(String msg);
}
