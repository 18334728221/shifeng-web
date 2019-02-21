package com.xwd.seller.model;

import com.auth.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frame.base.BaseEntity;

/**
 * @author ljl 手艺人
 */
public class Craftsman extends BaseEntity implements User<Long> {

	private static final long serialVersionUID = 1441648368379104649L;
	// columns START
	// id
	private Long id;
	// 名称
	private String name;
	// 密码
	@JsonIgnore
	private String password;
	// 手艺人编号
	private Long craftsmanNo;
	// 店铺名称
	private String shopName;
	// 性别 0:male 1:female
	private Boolean sex;
	// 所属地区
	private Long areaPlatMark;
	// 地址
	private String address;
	// 手机号
	private String mobile;
	// 身份证号
	private String identityCardNo;
	// 正面身份证图片
	private String cardPositiveImageId;
	// 反面身份证图片
	private String cardOppositeImageId;
	// 店铺背景图
	private String bgImageId;
	// 头像
	private String headImageId;

	// 图片服务器id
	private Long imageServerId;
	// 邮箱
	private String email;
	// 微信号
	private String wxId;
	// 微信
	private String weixin;
	// qq
	private String qq;
	// columns END
	private String sexString;

	// 店铺背景图
	private String bgImageUrl;
	// 头像
	private String headImageUrl;

	public Craftsman() {
	}

	public Craftsman(Long id) {
		this.id = id;
	}

	/**
	 * id
	 * 
	 * @return
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * id
	 * 
	 * @param value
	 */
	public void setId(Long value) {
		this.id = value;
	}

	/**
	 * 名称
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 名称
	 * 
	 * @param value
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * 密码
	 * 
	 * @return
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * 密码
	 * 
	 * @param value
	 */
	public void setPassword(String value) {
		this.password = value;
	}

	/**
	 * 手艺人编号
	 * 
	 * @return
	 */
	public Long getCraftsmanNo() {
		return this.craftsmanNo;
	}

	/**
	 * 手艺人编号
	 * 
	 * @param value
	 */
	public void setCraftsmanNo(Long value) {
		this.craftsmanNo = value;
	}

	/**
	 * 店铺名称
	 * 
	 * @return
	 */
	public String getShopName() {
		return this.shopName;
	}

	/**
	 * 店铺名称
	 * 
	 * @param value
	 */
	public void setShopName(String value) {
		this.shopName = value;
	}

	/**
	 * 性别 0:male 1:female
	 * 
	 * @return
	 */
	public Boolean getSex() {
		return this.sex;
	}

	/**
	 * 性别 0:male 1:female
	 * 
	 * @param value
	 */
	public void setSex(Boolean value) {
		this.sex = value;
	}

	/**
	 * 所属地区
	 * 
	 * @return
	 */
	public Long getAreaPlatMark() {
		return this.areaPlatMark;
	}

	/**
	 * 所属地区
	 * 
	 * @param value
	 */
	public void setAreaPlatMark(Long value) {
		this.areaPlatMark = value;
	}

	/**
	 * 地址
	 * 
	 * @return
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * 地址
	 * 
	 * @param value
	 */
	public void setAddress(String value) {
		this.address = value;
	}

	/**
	 * 手机号
	 * 
	 * @return
	 */
	public String getMobile() {
		return this.mobile;
	}

	/**
	 * 手机号
	 * 
	 * @param value
	 */
	public void setMobile(String value) {
		this.mobile = value;
	}

	/**
	 * 身份证号
	 * 
	 * @return
	 */
	public String getIdentityCardNo() {
		return this.identityCardNo;
	}

	/**
	 * 身份证号
	 * 
	 * @param value
	 */
	public void setIdentityCardNo(String value) {
		this.identityCardNo = value;
	}

	/**
	 * 正面身份证图片
	 * 
	 * @return
	 */
	public String getCardPositiveImageId() {
		return this.cardPositiveImageId;
	}

	/**
	 * 正面身份证图片
	 * 
	 * @param value
	 */
	public void setCardPositiveImageId(String value) {
		this.cardPositiveImageId = value;
	}

	/**
	 * 反面身份证图片
	 * 
	 * @return
	 */
	public String getCardOppositeImageId() {
		return this.cardOppositeImageId;
	}

	/**
	 * 反面身份证图片
	 * 
	 * @param value
	 */
	public void setCardOppositeImageId(String value) {
		this.cardOppositeImageId = value;
	}

	/**
	 * 图片服务器id
	 * 
	 * @return
	 */
	public Long getImageServerId() {
		return this.imageServerId;
	}

	/**
	 * 图片服务器id
	 * 
	 * @param value
	 */
	public void setImageServerId(Long value) {
		this.imageServerId = value;
	}

	/**
	 * 邮箱
	 * 
	 * @return
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * 邮箱
	 * 
	 * @param value
	 */
	public void setEmail(String value) {
		this.email = value;
	}

	/**
	 * 微信号
	 * 
	 * @return
	 */
	public String getWxId() {
		return this.wxId;
	}

	/**
	 * 微信号
	 * 
	 * @param value
	 */
	public void setWxId(String value) {
		this.wxId = value;
	}

	/**
	 * 微信
	 * 
	 * @return
	 */
	public String getWeixin() {
		return this.weixin;
	}

	/**
	 * 微信
	 * 
	 * @param value
	 */
	public void setWeixin(String value) {
		this.weixin = value;
	}

	/**
	 * qq
	 * 
	 * @return
	 */
	public String getQq() {
		return this.qq;
	}

	/**
	 * qq
	 * 
	 * @param value
	 */
	public void setQq(String value) {
		this.qq = value;
	}

	public String getSexString() {
		String sexString = "";
		if (this.sex != null && this.sex == true) {
			sexString = "男";
		} else if (this.sex != null && this.sex == false) {
			sexString = "女";
		}
		return sexString;
	}

	public void setSexString(String sexString) {
		this.sexString = sexString;
	}

	public String getBgImageId() {
		return bgImageId;
	}

	public void setBgImageId(String bgImageId) {
		this.bgImageId = bgImageId;
	}

	public String getHeadImageId() {
		return headImageId;
	}

	public void setHeadImageId(String headImageId) {
		this.headImageId = headImageId;
	}

	public String getBgImageUrl() {
		return bgImageUrl;
	}

	public void setBgImageUrl(String bgImageUrl) {
		this.bgImageUrl = bgImageUrl;
	}

	public String getHeadImageUrl() {
		return headImageUrl;
	}

	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}

}
