package com.xwd.auth.model;

import com.frame.base.BaseEntity;

/**
 * 用户关系
 * @author ljl
 *
 */
public class AuthUserRelation extends BaseEntity {
		
	private static final long serialVersionUID = 6494777423055073328L;
	//columns START
	//id
	private Long id;
	//userId
	private Long userId;
	//parentUserId
	private Long parentUserId;
	//columns END

	public AuthUserRelation(){
	}

	public AuthUserRelation(
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
	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	public void setParentUserId(Long value) {
		this.parentUserId = value;
	}
	
	public Long getParentUserId() {
		return this.parentUserId;
	}
}

