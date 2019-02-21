package com.xwd.msg.model;

import java.util.HashMap;
import java.util.Map;

import com.frame.base.BaseEntity;

/**
 * @author ljl 公司相关用户通知表
 */
public class SmsUser extends BaseEntity {

	private static final long serialVersionUID = 3553279416654271704L;
	// columns START
	// 用户ID
	private Long id;
	// userId
	private Long userId;
	// 用户名
	private String userName;
	// 短信模板ID
	private String smsTemplateId;
	// 标记时间
	private Long nanoTime;
	// columns END

	private Map<String, String> argMap = new HashMap<String, String>();
	
	private String mobile;

	public SmsUser() {
	}

	public SmsUser(Long id) {
		this.id = id;
	}

	/**
	 * 用户ID
	 * 
	 * @return
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * 用户ID
	 * 
	 * @param value
	 */
	public void setId(Long value) {
		this.id = value;
	}

	/**
	 * userId
	 * 
	 * @return
	 */
	public Long getUserId() {
		return this.userId;
	}

	/**
	 * userId
	 * 
	 * @param value
	 */
	public void setUserId(Long value) {
		this.userId = value;
	}

	/**
	 * 用户名
	 * 
	 * @return
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * 用户名
	 * 
	 * @param value
	 */
	public void setUserName(String value) {
		this.userName = value;
	}

	/**
	 * 短信模板ID
	 * 
	 * @return
	 */
	public String getSmsTemplateId() {
		return this.smsTemplateId;
	}

	/**
	 * 短信模板ID
	 * 
	 * @param value
	 */
	public void setSmsTemplateId(String value) {
		this.smsTemplateId = value;
	}

	/**
	 * 标记时间
	 * 
	 * @return
	 */
	public Long getNanoTime() {
		return this.nanoTime;
	}

	/**
	 * 标记时间
	 * 
	 * @param value
	 */
	public void setNanoTime(Long value) {
		this.nanoTime = value;
	}

	public Map<String, String> getArgMap() {
		return argMap;
	}
	
	public void setArgMap(Map<String, String> argMap) {
		if(argMap == null){
			return;
		}
		this.argMap = argMap;
	}

	public void addArgMap(String key, String value) {
		this.argMap.put(key, value);
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
