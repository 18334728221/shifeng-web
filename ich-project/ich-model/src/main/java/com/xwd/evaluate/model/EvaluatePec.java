package com.xwd.evaluate.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 用户产品评价内容
 */
public class EvaluatePec extends BaseEntity {
		
	private static final long serialVersionUID = -8770413071458655243L;
	//columns START
	//id
	private Long id;
	//顾客ID
	private Long customerNo;
	//产品评价内容ID
	private Long pecId;
	//columns END

	public EvaluatePec(){
	}

	public EvaluatePec(
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
	 * 产品评价内容ID
	 * @return
	 */
	public Long getPecId() {
		return this.pecId;
	}
	
	/**
	 * 产品评价内容ID
	 * @param value
	 */
	public void setPecId(Long value) {
		this.pecId = value;
	}

}

