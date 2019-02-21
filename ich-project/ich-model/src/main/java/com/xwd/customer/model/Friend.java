package com.xwd.customer.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 顾客朋友表
 */
public class Friend extends BaseEntity {
		
	private static final long serialVersionUID = -2565629528297510076L;
	//columns START
	//id
	private Long id;
	//顾客编号
	private Long customerNo;
	//顾客朋友ID
	private Long friendNo;
	//columns END

	public Friend(){
	}

	public Friend(
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
	 * 顾客编号
	 * @return
	 */
	public Long getCustomerNo() {
		return this.customerNo;
	}
	
	/**
	 * 顾客编号
	 * @param value
	 */
	public void setCustomerNo(Long value) {
		this.customerNo = value;
	}
	/**
	 * 顾客朋友ID
	 * @return
	 */
	public Long getFriendNo() {
		return this.friendNo;
	}
	
	/**
	 * 顾客朋友ID
	 * @param value
	 */
	public void setFriendNo(Long value) {
		this.friendNo = value;
	}
}

