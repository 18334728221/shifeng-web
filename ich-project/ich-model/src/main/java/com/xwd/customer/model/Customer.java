package com.xwd.customer.model;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.auth.User;
import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 顾客表
 */
public class Customer extends BaseEntity implements User<Long>{
		
	private static final long serialVersionUID = 87521713225275337L;
	public static final Byte USER_TYPE_CUSTOMER = 0;//普通用户
	public static final Byte USER_TYPE_BROKER = 1;//券商机构
	
	//columns START
	//id
	private Long id;
	//顾客编号
	private Long customerNo;
	//用户真实名称
	private String name;
	//用户密码
	@JsonIgnore
	private String password;
	//资金账户密码
	@JsonIgnore
	private String accountPassword;
	//昵称
	private String nickName;
	//手机号
	private String mobile;
	//家庭住址
	private String homeAddress;
	//出身日期
	private String birthday;
	//性别 0:male 1:female
	private Boolean sex;
	//头像ID
	private String imageId;
	//图片服务器ID
	private Long imageServerId;
	//地区标识
	private Long areaPlatMark;
	//微信号
	private String wxId;
	//微信昵称
	private String weixin;
	//qq
	private String qq;
	//email
	private String email;
	//身份证号
	private String identityCardNo;//@JsonIgnore
	//用户类型 0:普通用户 1:机构
	private Byte userType;
	//号段 10开始
	private Long sectionNo;
	//是否操盘手
	private Boolean isTrader;
	//手续费率
	private Float buyPoundageRate = 0f;
	private Float sellPoundageRate = 0f;
	//佣金比例
	private Float buyCommissionRate = 0f;
	private Float sellCommissionRate = 0f;
	//是否自动提现 0:否 1:是
	private Boolean isAutoWithdrawal;
	
	//会员级别
	private Byte level;
	//成长值
	private Integer growthValue;
	//是否实名认证
	private Boolean isAuthentication;
	private Boolean isAccountPwd;

	//columns END

	public Customer(){
	}

	public Customer(
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
	 * 顾客编号
	 * @return
	 */
	public Long getCustomerNo() {
		return this.customerNo;
	}
	
	/**
	 * 顾客编号
	 * @param value
	 */
	public void setCustomerNo(Long value) {
		this.customerNo = value;
	}
	/**
	 * 用户真实名称
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * 用户真实名称
	 * @param value
	 */
	public void setName(String value) {
		this.name = value;
	}
	/**
	 * 用户密码
	 * @return
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * 用户密码
	 * @param value
	 */
	public void setPassword(String value) {
		this.password = value;
	}
	
	public String getAccountPassword() {
		return accountPassword;
	}

	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
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
	 * 家庭住址
	 * @return
	 */
	public String getHomeAddress() {
		return this.homeAddress;
	}
	
	/**
	 * 家庭住址
	 * @param value
	 */
	public void setHomeAddress(String value) {
		this.homeAddress = value;
	}
	/**
	 * 出身日期
	 * @return
	 */
	public String getBirthday() {
		return this.birthday;
	}
	
	/**
	 * 出身日期
	 * @param value
	 */
	public void setBirthday(String value) {
		this.birthday = value;
	}
	/**
	 * 性别 0:male 1:female
	 * @return
	 */
	public Boolean getSex() {
		return this.sex;
	}
	
	/**
	 * 性别 0:male 1:female
	 * @param value
	 */
	public void setSex(Boolean value) {
		this.sex = value;
	}
	
	public Boolean getIsAuthentication() {
		return this.isAuthentication;
	}
	public void setIsAuthentication(Boolean value) {
		this.isAuthentication = value;
	}
	/**
	 * 头像ID
	 * @return
	 */
	public String getImageId() {
		return this.imageId;
	}
	
	/**
	 * 头像ID
	 * @param value
	 */
	public void setImageId(String value) {
		this.imageId = value;
	}
	/**
	 * 图片服务器ID
	 * @return
	 */
	public Long getImageServerId() {
		return this.imageServerId;
	}
	
	/**
	 * 图片服务器ID
	 * @param value
	 */
	public void setImageServerId(Long value) {
		this.imageServerId = value;
	}
	/**
	 * 地区标识
	 * @return
	 */
	public Long getAreaPlatMark() {
		return this.areaPlatMark;
	}
	
	/**
	 * 地区标识
	 * @param value
	 */
	public void setAreaPlatMark(Long value) {
		this.areaPlatMark = value;
	}
	/**
	 * 微信号
	 * @return
	 */
	public String getWxId() {
		return this.wxId;
	}
	
	/**
	 * 微信号
	 * @param value
	 */
	public void setWxId(String value) {
		this.wxId = value;
	}
	/**
	 * 微信昵称
	 * @return
	 */
	public String getWeixin() {
		return this.weixin;
	}
	
	/**
	 * 微信昵称
	 * @param value
	 */
	public void setWeixin(String value) {
		this.weixin = value;
	}
	/**
	 * qq
	 * @return
	 */
	public String getQq() {
		return this.qq;
	}
	
	/**
	 * qq
	 * @param value
	 */
	public void setQq(String value) {
		this.qq = value;
	}
	/**
	 * email
	 * @return
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * email
	 * @param value
	 */
	public void setEmail(String value) {
		this.email = value;
	}
	/**
	 * 身份证号
	 * @return
	 */
	public String getIdentityCardNo() {
		return this.identityCardNo;
	}
	
	/**
	 * 身份证号
	 * @param value
	 */
	public void setIdentityCardNo(String value) {
		this.identityCardNo = value;
	}
	/**
	 * 用户类型 0:普通用户 1:机构
	 * @return
	 */
	public Byte getUserType() {
		return this.userType;
	}
	
	/**
	 * 用户类型 0:普通用户 1:机构
	 * @param value
	 */
	public void setUserType(Byte value) {
		this.userType = value;
	}
	/**
	 * 号段 10开始
	 * @return
	 */
	public Long getSectionNo() {
		return this.sectionNo;
	}
	
	/**
	 * 号段 10开始
	 * @param value
	 */
	public void setSectionNo(Long value) {
		this.sectionNo = value;
	}
	
	public Float getBuyPoundageRate() {
		return buyPoundageRate;
	}

	public void setBuyPoundageRate(Float buyPoundageRate) {
		this.buyPoundageRate = buyPoundageRate;
	}

	public Float getSellPoundageRate() {
		return sellPoundageRate;
	}

	public void setSellPoundageRate(Float sellPoundageRate) {
		this.sellPoundageRate = sellPoundageRate;
	}

	public Float getBuyCommissionRate() {
		return buyCommissionRate;
	}

	public void setBuyCommissionRate(Float buyCommissionRate) {
		this.buyCommissionRate = buyCommissionRate;
	}

	public Float getSellCommissionRate() {
		return sellCommissionRate;
	}

	public void setSellCommissionRate(Float sellCommissionRate) {
		this.sellCommissionRate = sellCommissionRate;
	}

	/**
	 * 是否自动提现 0:否 1:是
	 * @return
	 */
	public Boolean getIsAutoWithdrawal() {
		return this.isAutoWithdrawal;
	}
	
	/**
	 * 是否自动提现 0:否 1:是
	 * @param value
	 */
	public void setIsAutoWithdrawal(Boolean value) {
		this.isAutoWithdrawal = value;
	}
	/**
	 * 会员级别
	 * @return
	 */
	public Byte getLevel() {
		return this.level;
	}
	
	/**
	 * 会员级别
	 * @param value
	 */
	public void setLevel(Byte value) {
		this.level = value;
	}
	
	public Integer getGrowthValue() {
		if(this.growthValue==null){
			growthValue=0;
		}
		return growthValue;
	}

	public void setGrowthValue(Integer growthValue) {
		this.growthValue = growthValue;
	}
	
	public Boolean getIsTrader() {
		return isTrader;
	}

	public void setIsTrader(Boolean isTrader) {
		this.isTrader = isTrader;
	}

	public Boolean getIsAccountPwd() {
		return isAccountPwd;
	}

	public void setIsAccountPwd(Boolean isAccountPwd) {
		this.isAccountPwd = isAccountPwd;
	}

}

