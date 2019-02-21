package com.xwd.auth.model;

import com.frame.base.BaseEntity;

/**
 * 组角色
 * @author ljl
 *
 */
public class AuthGroupRole extends BaseEntity {
	
	private static final long serialVersionUID = 4297962079080224143L;
	//columns START
	private Long groupId;
	private Long roleId;
	//columns END

	public AuthGroupRole(){
	}

	public AuthGroupRole(
		Long groupId,
		Long roleId
	){
		this.groupId = groupId;
		this.roleId = roleId;
	}

	public void setGroupId(Long value) {
		this.groupId = value;
	}
	
	public Long getGroupId() {
		return this.groupId;
	}
	public void setRoleId(Long value) {
		this.roleId = value;
	}
	
	public Long getRoleId() {
		return this.roleId;
	}
}

