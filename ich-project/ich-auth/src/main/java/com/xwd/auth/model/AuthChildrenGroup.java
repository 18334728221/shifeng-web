package com.xwd.auth.model;

import com.frame.base.BaseEntity;

/**
 * 组和子组关联表
 * @author ljl
 *
 */
public class AuthChildrenGroup extends BaseEntity {
	
	private static final long serialVersionUID = -7025205424749956902L;
	//columns START
	private Long Id;
	private Long groupId;
	private Long childrenGroupId;
	//columns END

	public AuthChildrenGroup(){
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public AuthChildrenGroup(
		Long groupId,
		Long childrenGroupId
	){
		this.groupId = groupId;
		this.childrenGroupId = childrenGroupId;
	}

	public void setGroupId(Long value) {
		this.groupId = value;
	}
	
	public Long getGroupId() {
		return this.groupId;
	}
	public void setChildrenGroupId(Long value) {
		this.childrenGroupId = value;
	}
	
	public Long getChildrenGroupId() {
		return this.childrenGroupId;
	}
}

