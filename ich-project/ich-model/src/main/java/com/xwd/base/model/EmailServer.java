package com.xwd.base.model;

import com.frame.base.BaseEntity;

/**
 * EmailServer
 * @author ljl
 */
public class EmailServer extends BaseEntity {

	private static final long serialVersionUID = -6024675629858069916L;
	
	// columns START
	// id
	private Long id;
	// SMTP主机
	private String smtp;
	// 发送邮箱
	private String fromName;
	// 发送邮箱密码
	private String password;
	// columns END

	public EmailServer() {
	}

	public EmailServer(Long id) {
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
	 * SMTP主机
	 * 
	 * @return
	 */
	public String getSmtp() {
		return this.smtp;
	}

	/**
	 * SMTP主机
	 * 
	 * @param value
	 */
	public void setSmtp(String value) {
		this.smtp = value;
	}

	/**
	 * 发送邮箱
	 * 
	 * @return
	 */
	public String getFromName() {
		return this.fromName;
	}

	/**
	 * 发送邮箱
	 * 
	 * @param value
	 */
	public void setFromName(String value) {
		this.fromName = value;
	}

	/**
	 * 发送邮箱密码
	 * 
	 * @return
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * 发送邮箱密码
	 * 
	 * @param value
	 */
	public void setPassword(String value) {
		this.password = value;
	}
}