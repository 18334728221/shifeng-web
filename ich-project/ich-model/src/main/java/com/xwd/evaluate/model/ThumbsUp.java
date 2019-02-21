package com.xwd.evaluate.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 点赞表
 */
public class ThumbsUp extends BaseEntity {
		
	private static final long serialVersionUID = -5825899076434385943L;
	//columns START
	//id
	private Long id;
	//评价id
	private Long evaluateId;
	//userId
	private Long userId;
	//顾客编号
	private Long customerNo;
	//columns END

	public ThumbsUp(){
	}

	public ThumbsUp(
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
	 * 评价id
	 * @return
	 */
	public Long getEvaluateId() {
		return this.evaluateId;
	}
	
	/**
	 * 评价id
	 * @param value
	 */
	public void setEvaluateId(Long value) {
		this.evaluateId = value;
	}
	/**
	 * userId
	 * @return
	 */
	public Long getUserId() {
		return this.userId;
	}
	
	/**
	 * userId
	 * @param value
	 */
	public void setUserId(Long value) {
		this.userId = value;
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
	
}

