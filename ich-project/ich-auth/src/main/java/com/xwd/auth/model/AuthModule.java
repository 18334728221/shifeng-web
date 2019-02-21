package com.xwd.auth.model;

import com.frame.base.BaseEntity;

/**
 * 模块
 * @author ljl
 *
 */
public class AuthModule extends BaseEntity {
	
	private static final long serialVersionUID = 7169563874655112428L;
	//columns START
	private Long id;
	private String moduleName;//模块名称
	private Long parentId;//父模块ID
	private Integer controlLevel;//权限控制级别
	//columns END
	private String controlLevelName;

	public AuthModule(){
	}

	public AuthModule(
		Long id
	){
		this.id = id;
	}

	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
	public void setModuleName(String value) {
		this.moduleName = value;
	}
	
	public String getModuleName() {
		return this.moduleName;
	}
	public void setParentId(Long value) {
		this.parentId = value;
	}
	
	public Long getParentId() {
		return this.parentId;
	}

	public Integer getControlLevel() {
		return controlLevel;
	}

	public void setControlLevel(Integer controlLevel) {
		this.controlLevel = controlLevel;
	}

	public String getControlLevelName() {
		return controlLevelName;
	}

	public void setControlLevelName(String controlLevelName) {
		this.controlLevelName = controlLevelName;
	}
	
}

