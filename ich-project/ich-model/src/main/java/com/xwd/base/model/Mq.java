package com.xwd.base.model;

import com.frame.base.BaseEntity;

/**
 * 消息中间件
 * 
 * @author ljl
 */
public class Mq extends BaseEntity {

	private static final long serialVersionUID = -7756139726618421236L;
	// columns START
	// id
	private Long id;
	// ip地址
	private String ip;
	// 端口
	private String port;

	// columns END

	public Mq() {
	}

	public Mq(Long id) {
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
	 * ip地址
	 * 
	 * @return
	 */
	public String getIp() {
		return this.ip;
	}

	/**
	 * ip地址
	 * 
	 * @param value
	 */
	public void setIp(String value) {
		this.ip = value;
	}

	/**
	 * 端口
	 * 
	 * @return
	 */
	public String getPort() {
		return this.port;
	}

	/**
	 * 端口
	 * 
	 * @param value
	 */
	public void setPort(String value) {
		this.port = value;
	}
}