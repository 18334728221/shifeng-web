package com.xwd.base.model;

import com.frame.base.BaseEntity;


/**
 * 系统配置表
 * @author lipw
 */
public class SysConfig extends BaseEntity {

	private static final long serialVersionUID = -8055049991299867220L;
	//columns START
	private Long id;
	//键
	private String configKey;
	//值
	private String configValue;
	//描述说明
	private String description;
	//columns END

	public SysConfig(){}

	public SysConfig(Long id){
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
	 * 键
	 * @return
	 */
	public String getConfigKey() {
		return this.configKey;
	}
	
	/**
	 * 键
	 * @param value
	 */
	public void setConfigKey(String value) {
		this.configKey = value;
	}
	/**
	 * 值
	 * @return
	 */
	public String getConfigValue() {
		return this.configValue;
	}
	
	/**
	 * 值
	 * @param value
	 */
	public void setConfigValue(String value) {
		this.configValue = value;
	}
	/**
	 * 描述说明
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * 描述说明
	 * @param value
	 */
	public void setDescription(String value) {
		this.description = value;
	}
}

