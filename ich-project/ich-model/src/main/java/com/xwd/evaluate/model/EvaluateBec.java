package com.xwd.evaluate.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 用户产品购买评价
 */
public class EvaluateBec extends BaseEntity {
		
	private static final long serialVersionUID = -5543821397006585066L;
	//columns START
	//id
	private Long id;
	//顾客ID
	private Long customerNo;
	//评价内容ID
	private Long becId;
	//星级
	private Integer starLevel;
	//columns END

	public EvaluateBec(){
	}

	public EvaluateBec(
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
	 * 顾客ID
	 * @return
	 */
	public Long getCustomerNo() {
		return this.customerNo;
	}
	
	/**
	 * 顾客ID
	 * @param value
	 */
	public void setCustomerNo(Long value) {
		this.customerNo = value;
	}
	/**
	 * 评价内容ID
	 * @return
	 */
	public Long getBecId() {
		return this.becId;
	}
	
	/**
	 * 评价内容ID
	 * @param value
	 */
	public void setBecId(Long value) {
		this.becId = value;
	}
	/**
	 * 星级
	 * @return
	 */
	public Integer getStarLevel() {
		return this.starLevel;
	}
	
	/**
	 * 星级
	 * @param value
	 */
	public void setStarLevel(Integer value) {
		this.starLevel = value;
	}
}

