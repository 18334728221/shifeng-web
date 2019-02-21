package com.xwd.msg.model;


import com.frame.base.BaseEntity;
import com.xwd.bean.MailMessage;

/**
 * @author ljl
 * 公司相关用户通知表
 */
public class EmailUser extends BaseEntity {
		
	private static final long serialVersionUID = -8253260042742756161L;
	//columns START
	//用户ID
	private Long id;
	//userId
	private Long userId;
	//用户名
	private String userName;
	//邮件标题
	private String title;
	//标记时间
	private Long nanoTime;
	//columns END
	
	private MailMessage mailMessage;

	public EmailUser(){
	}

	public EmailUser(
		Long id
	){
		this.id = id;
	}

	/**
	 * 用户ID
	 * @return
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * 用户ID
	 * @param value
	 */
	public void setId(Long value) {
		this.id = value;
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
	 * 用户名
	 * @return
	 */
	public String getUserName() {
		return this.userName;
	}
	
	/**
	 * 用户名
	 * @param value
	 */
	public void setUserName(String value) {
		this.userName = value;
	}
	/**
	 * 邮件标题
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * 邮件标题
	 * @param value
	 */
	public void setTitle(String value) {
		this.title = value;
	}
	/**
	 * 标记时间
	 * @return
	 */
	public Long getNanoTime() {
		return this.nanoTime;
	}
	
	/**
	 * 标记时间
	 * @param value
	 */
	public void setNanoTime(Long value) {
		this.nanoTime = value;
	}

	public MailMessage getMailMessage() {
		return mailMessage;
	}

	public void setMailMessage(MailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}
	
}

