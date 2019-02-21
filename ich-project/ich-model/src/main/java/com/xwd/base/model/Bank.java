package com.xwd.base.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * Bank
 */
public class Bank extends BaseEntity {
		
	private static final long serialVersionUID = -5313499881193504820L;
	//columns START
	//id
	private Long id;
	//卡号
	private String code;
	//银行名称
	private String name;
	//columns END

	public Bank(){
	}

	public Bank(
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
	 * 卡号
	 * @return
	 */
	public String getCode() {
		return this.code;
	}
	
	/**
	 * 卡号
	 * @param value
	 */
	public void setCode(String value) {
		this.code = value;
	}
	/**
	 * 银行名称
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * 银行名称
	 * @param value
	 */
	public void setName(String value) {
		this.name = value;
	}

}

