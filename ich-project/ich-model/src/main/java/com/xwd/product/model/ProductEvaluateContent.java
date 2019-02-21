package com.xwd.product.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * ProductEvaluateContent
 */
public class ProductEvaluateContent extends BaseEntity {

	private static final long serialVersionUID = 2222772670181481064L;
	//columns START
	//id
	private Long id;
	//产品编码
	private Long productCode;
	//内容
	private String content;
	//点评次数
	private Integer times;
	//columns END
	private String customerMobile;
	//星级
	private Integer starLevel;
	//点赞数量
	private Integer thumbsUpNumber;
	
	private String imageUrl;
	
	private String isThumbsUp;

	//产品名称
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public ProductEvaluateContent(){
	}

	public ProductEvaluateContent(
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
	 * 产品编码
	 * @return
	 */
	public Long getProductCode() {
		return this.productCode;
	}
	
	/**
	 * 产品编码
	 * @param value
	 */
	public void setProductCode(Long value) {
		this.productCode = value;
	}
	/**
	 * 内容
	 * @return
	 */
	public String getContent() {
		return this.content;
	}
	
	/**
	 * 内容
	 * @param value
	 */
	public void setContent(String value) {
		this.content = value;
	}
	/**
	 * 点评次数
	 * @return
	 */
	public Integer getTimes() {
		return this.times;
	}
	
	/**
	 * 点评次数
	 * @param value
	 */
	public void setTimes(Integer value) {
		this.times = value;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public Integer getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(Integer starLevel) {
		this.starLevel = starLevel;
	}

	public Integer getThumbsUpNumber() {
		return thumbsUpNumber;
	}

	public void setThumbsUpNumber(Integer thumbsUpNumber) {
		this.thumbsUpNumber = thumbsUpNumber;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getIsThumbsUp() {
		return isThumbsUp;
	}

	public void setIsThumbsUp(String isThumbsUp) {
		this.isThumbsUp = isThumbsUp;
	}
	
}

