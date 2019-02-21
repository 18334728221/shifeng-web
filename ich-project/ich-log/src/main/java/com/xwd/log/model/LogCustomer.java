package com.xwd.log.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl 顾客操作日志表
 */
public class LogCustomer extends BaseEntity {

	private static final long serialVersionUID = 5621740930165663282L;
	// columns START
	// id
	private Long id;
	// 用户ID
	private Long customerNo;
	// 用户名称
	private String name;
	// 用户IP
	private String ip;
	// 地区标识
	private Long areaPlatMark;
	// nanoTime
	private Long nanoTime;
	// columns END

	// 日志类别
	private Integer type;

	public LogCustomer() {
	}

	public LogCustomer(Long id) {
		this.id = id;
	}

	/**
	 * id
	 * 
	 * @return
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * id
	 * 
	 * @param value
	 */
	public void setId(Long value) {
		this.id = value;
	}

	/**
	 * 用户ID
	 * 
	 * @return
	 */
	public Long getCustomerNo() {
		return this.customerNo;
	}

	/**
	 * 用户ID
	 * 
	 * @param value
	 */
	public void setCustomerNo(Long value) {
		this.customerNo = value;
	}

	/**
	 * 用户名称
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 用户名称
	 * 
	 * @param value
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * 用户IP
	 * 
	 * @return
	 */
	public String getIp() {
		return this.ip;
	}

	/**
	 * 用户IP
	 * 
	 * @param value
	 */
	public void setIp(String value) {
		this.ip = value;
	}

	/**
	 * 地区标识
	 * 
	 * @return
	 */
	public Long getAreaPlatMark() {
		return this.areaPlatMark;
	}

	/**
	 * 地区标识
	 * 
	 * @param value
	 */
	public void setAreaPlatMark(Long value) {
		this.areaPlatMark = value;
	}

	/**
	 * 内容
	 * 
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * 内容
	 * 
	 * @param value
	 */
	public void setDescription(String value) {
		this.description = value;
	}

	/**
	 * nanoTime
	 * 
	 * @return
	 */
	public Long getNanoTime() {
		return this.nanoTime;
	}

	/**
	 * nanoTime
	 * 
	 * @param value
	 */
	public void setNanoTime(Long value) {
		this.nanoTime = value;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
}
