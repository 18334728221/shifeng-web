package com.xwd.auth.model;

import com.frame.base.BaseEntity;

/**
 * 角色权限
 * @author ljl
 *
 */
public class AuthRolePermission extends BaseEntity {
	
	private static final long serialVersionUID = 8182906309807771847L;
	//columns START
	private Long roleId;
	private Long permissionId;
	//columns END

	public AuthRolePermission(){
	}

	public AuthRolePermission(
		Long roleId,
		Long permissionId
	){
		this.roleId = roleId;
		this.permissionId = permissionId;
	}

	public void setRoleId(Long value) {
		this.roleId = value;
	}
	
	public Long getRoleId() {
		return this.roleId;
	}
	public void setPermissionId(Long value) {
		this.permissionId = value;
	}
	
	public Long getPermissionId() {
		return this.permissionId;
	}
}

