package com.xwd.log.model;

import java.util.Calendar;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 系统用户操作日志表
 */
public class LogUser extends BaseEntity {
		
	
	private static final long serialVersionUID = -6375723726359811441L;
	//columns START
	//id
	private Long id;
	//用户ID
	private Long userId;
	//用户名称
	private String userName;
	//用户IP
	private String userIp;
	//nanoTime
	private Long nanoTime;
	//columns END
	//日志类别
	private Integer type;
	
	//结束时间
	private Calendar createTime;

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public LogUser(){
	}

	public LogUser(
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
	 * 用户ID
	 * @return
	 */
	public Long getUserId() {
		return this.userId;
	}
	
	/**
	 * 用户ID
	 * @param value
	 */
	public void setUserId(Long value) {
		this.userId = value;
	}
	/**
	 * 用户名称
	 * @return
	 */
	public String getUserName() {
		return this.userName;
	}
	
	/**
	 * 用户名称
	 * @param value
	 */
	public void setUserName(String value) {
		this.userName = value;
	}
	/**
	 * 用户IP
	 * @return
	 */
	public String getUserIp() {
		return this.userIp;
	}
	
	/**
	 * 用户IP
	 * @param value
	 */
	public void setUserIp(String value) {
		this.userIp = value;
	}
	/**
	 * 内容
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * 内容
	 * @param value
	 */
	public void setDescription(String value) {
		this.description = value;
	}
	/**
	 * nanoTime
	 * @return
	 */
	public Long getNanoTime() {
		return this.nanoTime;
	}
	
	/**
	 * nanoTime
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

