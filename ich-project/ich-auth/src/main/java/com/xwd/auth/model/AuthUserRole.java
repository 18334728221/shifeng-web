package com.xwd.auth.model;

import com.frame.base.BaseEntity;

/**
 * 用户角色
 * @author ljl
 *
 */
public class AuthUserRole extends BaseEntity {
	
	private static final long serialVersionUID = -6083856031225317051L;
	//columns START
	private Long userId;
	private Long roleId;
	//columns END

	public AuthUserRole(){
	}

	public AuthUserRole(
		Long userId,
		Long roleId
	){
		this.userId = userId;
		this.roleId = roleId;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	public void setRoleId(Long value) {
		this.roleId = value;
	}
	
	public Long getRoleId() {
		return this.roleId;
	}
}

