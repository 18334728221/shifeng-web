package com.xwd.msg.model;

import java.util.HashMap;
import java.util.Map;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 顾客通知表
 */
public class SmsCustomer extends BaseEntity {
		
	private static final long serialVersionUID = 3341664739584736595L;
	//columns START
	//id
	private Long id;
	//顾客编号
	private Long customerNo;
	//老师姓名
	private String customerName;
	//手机号码
	private String mobile;
	//短信模板ID
	private String smsTemplateId;
	//地区标识
	private Long areaPlatMark;
	//标记时间
	private Long nanoTime;
	//columns END
	
	private Map<String, String> argMap = new HashMap<String, String>();

	public SmsCustomer(){
	}

	public SmsCustomer(
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
	 * 老师姓名
	 * @return
	 */
	public String getCustomerName() {
		return this.customerName;
	}
	
	/**
	 * 老师姓名
	 * @param value
	 */
	public void setCustomerName(String value) {
		this.customerName = value;
	}
	/**
	 * 手机号码
	 * @return
	 */
	public String getMobile() {
		return this.mobile;
	}
	
	/**
	 * 手机号码
	 * @param value
	 */
	public void setMobile(String value) {
		this.mobile = value;
	}
	/**
	 * 短信模板ID
	 * @return
	 */
	public String getSmsTemplateId() {
		return this.smsTemplateId;
	}
	
	/**
	 * 短信模板ID
	 * @param value
	 */
	public void setSmsTemplateId(String value) {
		this.smsTemplateId = value;
	}
	/**
	 * 地区标识
	 * @return
	 */
	public Long getAreaPlatMark() {
		return this.areaPlatMark;
	}
	
	/**
	 * 地区标识
	 * @param value
	 */
	public void setAreaPlatMark(Long value) {
		this.areaPlatMark = value;
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
	
	public void setArgMap(Map<String, String> argMap) {
		if(argMap == null){
			return;
		}
		this.argMap = argMap;
	}

	public Map<String, String> getArgMap() {
		return argMap;
	}

	public void addArgMap(String key, String value) {
		this.argMap.put(key, value);
	}
	
}

