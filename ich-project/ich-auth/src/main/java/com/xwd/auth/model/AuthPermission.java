package com.xwd.auth.model;

import com.frame.base.BaseEntity;

/**
 * 权限
 * @author ljl
 *
 */
public class AuthPermission extends BaseEntity {
	
	private static final long serialVersionUID = -8798838083411411705L;
	public static final int PERMISSION_TYPE_OPERATION = 1;
	public static final int PERMISSION_TYPE_LEVEL = 2;
	
	//columns START
	private Long id;
	private Long moduleId;//模块id
	private String target;//权限码
	private String operation;//操作码
	private Byte permissionType;//操作类型
	private String description;//功能描述
	private Integer controlLevel;//权限控制级别
	//columns END

	private String moduleName;
	private String controlLevelName;
	
	public AuthPermission(){
	}

	public AuthPermission(
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
	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public void setTarget(String value) {
		this.target = value;
	}
	
	public String getTarget() {
		return this.target;
	}
	public void setOperation(String value) {
		this.operation = value;
	}
	
	public String getOperation() {
		return this.operation;
	}
	public void setPermissionType(Byte value) {
		this.permissionType = value;
	}
	
	public Byte getPermissionType() {
		return this.permissionType;
	}
	public void setDescription(String value) {
		this.description = value;
	}
	
	public String getDescription() {
		return this.description;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
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

