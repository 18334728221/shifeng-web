package com.xwd.customer.model;

import java.math.BigDecimal;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 顾客收藏表
 */
public class CustomerChoice extends BaseEntity {
	
	private static final long serialVersionUID = -7793965833453987372L;
	//columns START
	//id
	private Long id;
	//顾客编号
	private Long customerNo;
	//产品编号
	private Long productCode;
	//1:商城产品收藏 
	private Boolean type;
	//columns END
	
	private String productName;
	private String imageUrl;
	private BigDecimal price;
	private Float priceFluctuation;

	public CustomerChoice(){
	}

	public CustomerChoice(
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
	 * 产品编号
	 * @return
	 */
	public Long getProductCode() {
		return this.productCode;
	}
	
	/**
	 * 产品编号
	 * @param value
	 */
	public void setProductCode(Long value) {
		this.productCode = value;
	}
	/**
	 * 1:商城产品收藏 
	 * @return
	 */
	public Boolean getType() {
		return this.type;
	}
	
	/**
	 * 1:商城产品收藏 
	 * @param value
	 */
	public void setType(Boolean value) {
		this.type = value;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Float getPriceFluctuation() {
		return priceFluctuation;
	}

	public void setPriceFluctuation(Float priceFluctuation) {
		this.priceFluctuation = priceFluctuation;
	}
	
}

