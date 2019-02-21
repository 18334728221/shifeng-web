package com.xwd.bean;

import java.io.Serializable;

/**
 * websocket消息bean
 * @author ljl
 *
 */
public class WebsocketMessage implements Serializable{

	private static final long serialVersionUID = 2250868394882106642L;
	
	private String msgType;//消息类型
	private String msg;//消息 json串
	
	/**
	 * 如果为null群发
	 * @return
	 */
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
