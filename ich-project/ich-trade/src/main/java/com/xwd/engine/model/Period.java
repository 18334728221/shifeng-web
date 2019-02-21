package com.xwd.engine.model;

import java.io.Serializable;

/**
 * 时间段
 * @author ljl
 *
 */
public class Period implements Serializable{

	private static final long serialVersionUID = -2420971450958794446L;
	
	private String from;
	private String to;
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
	public Long getFromTime(){
		if(from == null){
			return 0L;
		} else {
			return Long.valueOf(from.replaceAll(":", ""));
		}
	}
	
	public Long getToTime(){
		if(to == null){
			return 0L;
		} else {
			return Long.valueOf(to.replaceAll(":", ""));
		}
	}

}
