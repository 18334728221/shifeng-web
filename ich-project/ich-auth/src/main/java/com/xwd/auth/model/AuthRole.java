package com.xwd.auth.model;

import com.frame.base.BaseEntity;

/**
 * 角色
 * @author ljl
 *
 */
public class AuthRole extends BaseEntity {
		
	private static final long serialVersionUID = 1064068979911478378L;
	//columns START
	//id
	private Long id;
	//角色名称
	private String roleName;
	//是否系统内定角色
	private Boolean isInnerRole = false;
	//描述
	private String description;
	//是否用户类型
	private Boolean isUserType = false;
	//是否前端访问
	private Boolean isFrontAccesse = false;
	//是否后台访问
	private Boolean isBackgroundAccesse = false;
	//columns END

	public AuthRole(){
	}

	public AuthRole(
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
	public void setRoleName(String value) {
		this.roleName = value;
	}
	
	public String getRoleName() {
		return this.roleName;
	}
	
	
	public Boolean getIsUserType() {
		return isUserType;
	}
	
	public Boolean isUserType() {
		return isUserType;
	}

	public void setIsUserType(Boolean isUserType) {
		this.isUserType = isUserType;
	}
	
	public Boolean getIsFrontAccesse() {
		return isFrontAccesse;
	}
	
	public Boolean isFrontAccesse() {
		return isFrontAccesse;
	}

	public void setIsFrontAccesse(Boolean isFrontAccesse) {
		this.isFrontAccesse = isFrontAccesse;
	}

	public Boolean getIsBackgroundAccesse() {
		return isBackgroundAccesse;
	}
	
	public Boolean isBackgroundAccesse() {
		return isBackgroundAccesse;
	}

	public void setIsBackgroundAccesse(Boolean isBackgroundAccesse) {
		this.isBackgroundAccesse = isBackgroundAccesse;
	}

	public void setIsInnerRole(Boolean isInnerRole) {
		this.isInnerRole = isInnerRole;
	}
	
	public Boolean isInnerRole(){
		return isInnerRole;
	}
	
	public Boolean getIsInnerRole() {
		return isInnerRole;
	}

	public void setDescription(String value) {
		this.description = value;
	}
	
	public String getDescription() {
		return this.description;
	}
}

