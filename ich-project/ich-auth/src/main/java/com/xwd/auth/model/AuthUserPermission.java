package com.xwd.auth.model;

import com.frame.base.BaseEntity;

/**
 * 用户权限
 * @author ljl
 *
 */
public class AuthUserPermission extends BaseEntity {
	
	private static final long serialVersionUID = -51737261809943696L;
	//columns START
	private Long userId;
	private Long permissionId;
	//columns END

	public AuthUserPermission(){
	}

	public AuthUserPermission(
		Long userId,
		Long permissionId
	){
		this.userId = userId;
		this.permissionId = permissionId;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	public void setPermissionId(Long value) {
		this.permissionId = value;
	}
	
	public Long getPermissionId() {
		return this.permissionId;
	}
}

