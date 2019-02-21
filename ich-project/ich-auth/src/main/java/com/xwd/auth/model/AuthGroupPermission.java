package com.xwd.auth.model;

import com.frame.base.BaseEntity;

/**
 * 组功能
 * @author ljl
 *
 */
public class AuthGroupPermission extends BaseEntity {
	
	private static final long serialVersionUID = 7667692077808714573L;
	
	//columns START
	private Long groupId;
	private Long permissionId;
	//columns END

	public AuthGroupPermission(){
	}

	public AuthGroupPermission(
		Long groupId,
		Long permissionId
	){
		this.groupId = groupId;
		this.permissionId = permissionId;
	}

	public void setGroupId(Long value) {
		this.groupId = value;
	}
	
	public Long getGroupId() {
		return this.groupId;
	}
	public void setPermissionId(Long value) {
		this.permissionId = value;
	}
	
	public Long getPermissionId() {
		return this.permissionId;
	}
}

