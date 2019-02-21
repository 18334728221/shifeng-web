package com.xwd.base.model;

import com.frame.base.BaseEntity;

/**
 * MqTheme
 * 
 * @author ljl
 */
public class MqTheme extends BaseEntity {

	private static final long serialVersionUID = -5876474285220575088L;
	// columns START
	// id
	private Long id;
	// 主题名称
	private String topic;
	// 标记
	private String tags;
	//键
	private String mqKeys;
	// columns END

	public MqTheme() {
	}

	public MqTheme(Long id) {
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
	 * 主题名称
	 * 
	 * @return
	 */
	public String getTopic() {
		return this.topic;
	}

	/**
	 * 主题名称
	 * 
	 * @param value
	 */
	public void setTopic(String value) {
		this.topic = value;
	}

	/**
	 * 标记
	 * 
	 * @return
	 */
	public String getTags() {
		return this.tags;
	}

	/**
	 * 标记
	 * 
	 * @param value
	 */
	public void setTags(String value) {
		this.tags = value;
	}
	
	public String getMqKeys() {
		return mqKeys;
	}

	public void setMqKeys(String mqKeys) {
		this.mqKeys = mqKeys;
	}
}
