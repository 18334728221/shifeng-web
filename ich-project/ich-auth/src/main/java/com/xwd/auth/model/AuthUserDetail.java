package com.xwd.auth.model;


import com.frame.base.BaseEntity;


/**
 * @author ljl
 */
/**
 * AuthUserDetail
 */
public class AuthUserDetail extends BaseEntity {
		
	private static final long serialVersionUID = 2446812085185370129L;
	//columns START
	//id
	private Long id;
	//userId
	private Long userId;
	//userLocalId
	private String userLocalId;
	//昵称
	private String nickName;
	//性别 0:女 1:男
	private Boolean sex;
	//日期
	private String birthday;
	//学校
	private String school;
	//学位
	private String education;
	//学历
	private String degree;
	//座右铭
	private String motto;
	//columns END

	public AuthUserDetail(){
	}

	public AuthUserDetail(
		Long id
	){
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
	 * userId
	 * @return
	 */
	public Long getUserId() {
		return this.userId;
	}
	
	/**
	 * userId
	 * @param value
	 */
	public void setUserId(Long value) {
		this.userId = value;
	}
	/**
	 * userLocalId
	 * @return
	 */
	public String getUserLocalId() {
		return this.userLocalId;
	}
	
	/**
	 * userLocalId
	 * @param value
	 */
	public void setUserLocalId(String value) {
		this.userLocalId = value;
	}
	/**
	 * 昵称
	 * @return
	 */
	public String getNickName() {
		return this.nickName;
	}
	
	/**
	 * 昵称
	 * @param value
	 */
	public void setNickName(String value) {
		this.nickName = value;
	}
	/**
	 * 性别 0:女 1:男
	 * @return
	 */
	public Boolean getSex() {
		return this.sex;
	}
	
	/**
	 * 性别 0:女 1:男
	 * @param value
	 */
	public void setSex(Boolean value) {
		this.sex = value;
	}
	/**
	 * 日期
	 * @return
	 */
	public String getBirthday() {
		return this.birthday;
	}
	
	/**
	 * 日期
	 * @param value
	 */
	public void setBirthday(String value) {
		this.birthday = value;
	}
	/**
	 * 学校
	 * @return
	 */
	public String getSchool() {
		return this.school;
	}
	
	/**
	 * 学校
	 * @param value
	 */
	public void setSchool(String value) {
		this.school = value;
	}
	/**
	 * 学位
	 * @return
	 */
	public String getEducation() {
		return this.education;
	}
	
	/**
	 * 学位
	 * @param value
	 */
	public void setEducation(String value) {
		this.education = value;
	}
	/**
	 * 学历
	 * @return
	 */
	public String getDegree() {
		return this.degree;
	}
	
	/**
	 * 学历
	 * @param value
	 */
	public void setDegree(String value) {
		this.degree = value;
	}
	/**
	 * 座右铭
	 * @return
	 */
	public String getMotto() {
		return this.motto;
	}
	
	/**
	 * 座右铭
	 * @param value
	 */
	public void setMotto(String value) {
		this.motto = value;
	}
	
}

