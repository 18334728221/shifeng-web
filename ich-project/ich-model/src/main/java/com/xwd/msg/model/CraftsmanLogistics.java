package com.xwd.msg.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 物流信息
 */
public class CraftsmanLogistics extends BaseEntity {
		
	//columns START
	//id
	private Long id;
	//订单号
	private String orderNo;
	//用户编号
	private Long customerNo;
	//物流单号
	private String trackingNo;
	//物流信息
	private String logisticsInformation;
	//columns END

	public CraftsmanLogistics(){
	}

	public CraftsmanLogistics(
		Long id
	){
		this.id = id;
	}

	/**
	 * id
	 * @return
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * id
	 * @param value
	 */
	public void setId(Long value) {
		this.id = value;
	}
	/**
	 * 订单号
	 * @return
	 */
	public String getOrderNo() {
		return this.orderNo;
	}
	
	/**
	 * 订单号
	 * @param value
	 */
	public void setOrderNo(String value) {
		this.orderNo = value;
	}
	/**
	 * 用户编号
	 * @return
	 */
	public Long getCustomerNo() {
		return this.customerNo;
	}
	
	/**
	 * 用户编号
	 * @param value
	 */
	public void setCustomerNo(Long value) {
		this.customerNo = value;
	}
	/**
	 * 物流单号
	 * @return
	 */
	public String getTrackingNo() {
		return this.trackingNo;
	}
	
	/**
	 * 物流单号
	 * @param value
	 */
	public void setTrackingNo(String value) {
		this.trackingNo = value;
	}
	/**
	 * 物流信息
	 * @return
	 */
	public String getLogisticsInformation() {
		return logisticsInformation;
	}

	public void setLogisticsInformation(String logisticsInformation) {
		this.logisticsInformation = logisticsInformation;
	}
	

}

