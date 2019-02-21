package com.xwd.auth.model;

import com.auth.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frame.base.BaseEntity;

/**
 * 公司用户表
 * @author sf
 */
public class AuthUser extends BaseEntity implements User<Long>{

	private static final long serialVersionUID = -4463894953406841769L;
	//date formats
	//id
	private Long id;
	//用户账号
	private String name;
	//密码 盐值+MD5加密保存
	@JsonIgnore
	private String password;
	//手机号
	private String mobile;
	//电子邮箱
	private String email;
	//真实姓名
	private String trueName;
	//本地数据库id
	private String localId;
	//头像路径Id
	private String imageId;
	//图片服务器id
	private Long imageServerId;
	//用户类型
	private Integer userType;
	//columns END
	
	//是否是自身平台的用户
	private boolean isSelfUser = true;
	private String imageUrl;
		
	public AuthUser(){}

	public AuthUser(Long id){
		this.id = id;
	}

	/**
	 * id
	 * @return
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * id
	 * @param value
	 */
	public void setId(Long value) {
		this.id = value;
	}
	/**
	 * 用户账号
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * 用户账号
	 * @param value
	 */
	public void setName(String value) {
		this.name = value;
	}
	/**
	 * 密码 盐值+MD5加密保存
	 * @return
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * 密码 盐值+MD5加密保存
	 * @param value
	 */
	public void setPassword(String value) {
		this.password = value;
	}
	/**
	 * 手机号
	 * @return
	 */
	public String getMobile() {
		return this.mobile;
	}
	
	/**
	 * 手机号
	 * @param value
	 */
	public void setMobile(String value) {
		this.mobile = value;
	}
	/**
	 * 电子邮箱
	 * @return
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * 电子邮箱
	 * @param value
	 */
	public void setEmail(String value) {
		this.email = value;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	/**
	 * 真实姓名
	 * @return
	 */
	public String getTrueName() {
		return this.trueName;
	}
	
	/**
	 * 真实姓名
	 * @param value
	 */
	public void setTrueName(String value) {
		this.trueName = value;
	}
	/**
	 * 本地数据库id
	 * @return
	 */
	public String getLocalId() {
		return this.localId;
	}
	
	/**
	 * 本地数据库id
	 * @param value
	 */
	public void setLocalId(String value) {
		this.localId = value;
	}
	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public Long getImageServerId() {
		return imageServerId;
	}

	public void setImageServerId(Long imageServerId) {
		this.imageServerId = imageServerId;
	}
	
	public boolean isSelfUser() {
		return isSelfUser;
	}

	public void setSelfUser(boolean isSelfUser) {
		this.isSelfUser = isSelfUser;
	}
	
	public boolean getIsSelfUser(){
		return this.isSelfUser;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}

