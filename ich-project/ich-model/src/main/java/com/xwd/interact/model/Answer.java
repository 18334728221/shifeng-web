package com.xwd.interact.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 商品答表
 */
public class Answer extends BaseEntity {
		
	private static final long serialVersionUID = 3414136382578701504L;
	//columns START
	//id
	private Long id;
	//问题编号
	private String questionNo;
	//顾客编号
	private Long customerNo;
	//用户编号
	private Long userId;
	//内容
	private String content;
	//columns END

	public Answer(){
	}

	public Answer(
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
	 * 用户编号
	 * @return
	 */
	public Long getUserId() {
		return this.userId;
	}
	
	/**
	 * 用户编号
	 * @param value
	 */
	public void setUserId(Long value) {
		this.userId = value;
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

