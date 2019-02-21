package com.xwd.customer.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 客户关系表
 */
public class CustomerRelation extends BaseEntity {
		
	private static final long serialVersionUID = -2960123245489385477L;
	//columns START
	//id
	private Long id;
	//顾客编号
	private Long customerNo;
	//推荐人NO
	private Long superNo;
	//columns END

	public CustomerRelation(){
	}

	public CustomerRelation(
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
	 * 推荐人NO
	 * @return
	 */
	public Long getSuperNo() {
		return this.superNo;
	}
	
	/**
	 * 推荐人NO
	 * @param value
	 */
	public void setSuperNo(Long value) {
		this.superNo = value;
	}
}

