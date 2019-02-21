package com.xwd.interact.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 商品问题表
 */
public class Question extends BaseEntity {
		
	private static final long serialVersionUID = 3926755176970996221L;
	//columns START
	//id
	private Long id;
	//问题编号
	private String questionNo;
	//顾客编号
	private Long customerNo;
	//产品编号
	private Long productCode;
	//内容
	private String content;
	//columns END

	public Question(){
	}

	public Question(
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
	 * 问题编号
	 * @return
	 */
	public String getQuestionNo() {
		return this.questionNo;
	}
	
	/**
	 * 问题编号
	 * @param value
	 */
	public void setQuestionNo(String value) {
		this.questionNo = value;
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
	 * 产品编号
	 * @return
	 */
	public Long getProductCode() {
		return this.productCode;
	}
	
	/**
	 * 产品编号
	 * @param value
	 */
	public void setProductCode(Long value) {
		this.productCode = value;
	}
	/**
	 * 内容
	 * @return
	 */
	public String getContent() {
		return this.content;
	}
	
	/**
	 * 内容
	 * @param value
	 */
	public void setContent(String value) {
		this.content = value;
	}
}

