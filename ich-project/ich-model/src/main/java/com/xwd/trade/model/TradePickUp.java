package com.xwd.trade.model;

import java.util.Calendar;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 提货
 */
public class TradePickUp extends BaseEntity {
		
	private static final long serialVersionUID = -7433281832425285200L;
	//columns START
	//id
	private Long id;
	//产品编号
	private Long code;
	//sku码
	private String sku;
	//收货地址
	private Long addressId;
	//顾客编号
	private Long customerNo;
	//产品数量
	private Long num;
	//提货日期
	private Calendar pickUpTime;
	//收货日期
	private Calendar receiveTime;

	public TradePickUp(){
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public Long getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(Long customerNo) {
		this.customerNo = customerNo;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public Calendar getPickUpTime() {
		return pickUpTime;
	}

	public void setPickUpTime(Calendar pickUpTime) {
		this.pickUpTime = pickUpTime;
	}

	public Calendar getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Calendar receiveTime) {
		this.receiveTime = receiveTime;
	}
	
}

