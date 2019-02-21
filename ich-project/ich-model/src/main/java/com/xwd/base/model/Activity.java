package com.xwd.base.model;

import java.util.Calendar;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 活动表
 */
public class Activity extends BaseEntity {
		
	private static final long serialVersionUID = 976967666830942702L;
	//columns START
	//id
	private Long id;
	//产品编号
	private String productCode;
	//产品名称
	private String productName;
	//imageId
	private String imageId;
	//imageServerId
	private Long imageServerId;
	//活动URL
	private String url;
	//活动开始时间
	private Calendar startTime;
	//活动结束时间
	private Calendar endTime;
	//活动结束时间
	private Integer stauts;
	//columns END
	private String imageUrl;
	private String categoryName;

	public Activity(){
	}

	public Activity(
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
	 * 产品编号
	 * @return
	 */
	public String getProductCode() {
		return this.productCode;
	}
	
	/**
	 * 产品编号
	 * @param value
	 */
	public void setProductCode(String value) {
		this.productCode = value;
	}
	/**
	 * imageId
	 * @return
	 */
	public String getImageId() {
		return this.imageId;
	}
	
	/**
	 * imageId
	 * @param value
	 */
	public void setImageId(String value) {
		this.imageId = value;
	}
	/**
	 * imageServerId
	 * @return
	 */
	public Long getImageServerId() {
		return this.imageServerId;
	}
	
	/**
	 * imageServerId
	 * @param value
	 */
	public void setImageServerId(Long value) {
		this.imageServerId = value;
	}
	/**
	 * 活动URL
	 * @return
	 */
	public String getUrl() {
		return this.url;
	}
	
	/**
	 * 活动URL
	 * @param value
	 */
	public void setUrl(String value) {
		this.url = value;
	}
	/**
	 * 活动开始时间
	 * @return
	 */
	public String getStartTimeString() {
		return date2String(getStartTime(), DATE_TIME_FORMAT);
	}
	
	/**
	 * 活动开始时间
	 * @param value
	 */
	public void setStartTimeString(String value) {
		setStartTime(string2Date(value, DATE_TIME_FORMAT));
	}
	
	/**
	 * 活动开始时间
	 * @return
	 */
	public Calendar getStartTime() {
		return this.startTime;
	}
	
	/**
	 * 活动开始时间
	 * @param value
	 */
	public void setStartTime(Calendar value) {
		this.startTime = value;
	}
	/**
	 * 活动结束时间
	 * @return
	 */
	public String getEndTimeString() {
		return date2String(getEndTime(), DATE_TIME_FORMAT);
	}
	
	/**
	 * 活动结束时间
	 * @param value
	 */
	public void setEndTimeString(String value) {
		setEndTime(string2Date(value, DATE_TIME_FORMAT));
	}
	
	/**
	 * 活动结束时间
	 * @return
	 */
	public Calendar getEndTime() {
		return this.endTime;
	}
	
	/**
	 * 活动结束时间
	 * @param value
	 */
	public void setEndTime(Calendar value) {
		this.endTime = value;
	}

	public Integer getStauts() {
		return stauts;
	}

	public void setStauts(Integer stauts) {
		this.stauts = stauts;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}

