package com.xwd.customer.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl Address
 */
public class Address extends BaseEntity {
	private static final long serialVersionUID = 1541708099400479907L;
	// columns START
	// id
	private Long id;
	// 顾客编号
	private Long customerNo;
	// 收货人
	private String consignee;
	// 手机号
	private String mobile;
	// 所在地区
	private String location;
	// 详细地址
	private String detailedAddress;
	// 是否默认地址 0:否 1:是
	private Boolean isDefault;
	// columns END
	private String customerName;
	public Address() {
	}

	public Address(Long id) {
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
	 * 顾客编号
	 * 
	 * @return
	 */
	public Long getCustomerNo() {
		return this.customerNo;
	}

	/**
	 * 顾客编号
	 * 
	 * @param value
	 */
	public void setCustomerNo(Long value) {
		this.customerNo = value;
	}

	/**
	 * 收货人
	 * 
	 * @return
	 */
	public String getConsignee() {
		return this.consignee;
	}

	/**
	 * 收货人
	 * 
	 * @param value
	 */
	public void setConsignee(String value) {
		this.consignee = value;
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
	 * 所在地区
	 * 
	 * @return
	 */
	public String getLocation() {
		return this.location;
	}

	/**
	 * 所在地区
	 * 
	 * @param value
	 */
	public void setLocation(String value) {
		this.location = value;
	}

	/**
	 * 详细地址
	 * 
	 * @return
	 */
	public String getDetailedAddress() {
		return this.detailedAddress;
	}

	/**
	 * 详细地址
	 * 
	 * @param value
	 */
	public void setDetailedAddress(String value) {
		this.detailedAddress = value;
	}

	/**
	 * 是否默认地址 0:否 1:是
	 * 
	 * @return
	 */
	public Boolean isDefault() {
		return this.isDefault;
	}

	/**
	 * 是否默认地址 0:否 1:是
	 * 
	 * @param value
	 */
	public void setIsDefault(Boolean value) {
		this.isDefault = value;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
}
