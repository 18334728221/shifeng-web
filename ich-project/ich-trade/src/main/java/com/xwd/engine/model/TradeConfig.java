package com.xwd.engine.model;

import java.io.Serializable;
import java.util.List;

public class TradeConfig implements Serializable {

	private static final long serialVersionUID = -4484186413902869646L;
	//开始下单时间
	private String orderTime;
	private List<Period> periods;
	//集合竞价时间
	private List<Period> callAuctionPeriods;

	public List<Period> getPeriods() {
		return periods;
	}

	public void setPeriods(List<Period> periods) {
		this.periods = periods;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	
	public List<Period> getCallAuctionPeriods() {
		return callAuctionPeriods;
	}

	public void setCallAuctionPeriods(List<Period> callAuctionPeriods) {
		this.callAuctionPeriods = callAuctionPeriods;
	}

	public Long getOrderTimeAsLong(){
		if(orderTime == null){
			return 0L;
		} else {
			return Long.valueOf(orderTime.replaceAll(":", ""));
		}
	}

}
