package com.xwd.auth.model;

import com.frame.base.BaseEntity;

/**
 * 用户组
 * @author ljl
 *
 */
public class AuthUserGroup extends BaseEntity {
	
	private static final long serialVersionUID = 1162048559153142231L;

	//columns START
	private Long userId;
	private Long groupId;
	//columns END

	public AuthUserGroup(){
	}

	public AuthUserGroup(
		Long userId,
		Long groupId
	){
		this.userId = userId;
		this.groupId = groupId;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	public void setGroupId(Long value) {
		this.groupId = value;
	}
	
	public Long getGroupId() {
		return this.groupId;
	}
}

