package com.xwd.product.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 产品视频
 */
public class ProductVideo extends BaseEntity {
		
	private static final long serialVersionUID = -4753046517272868647L;
	//columns START
	//id
	private Long id;
	//productCode
	private Long productCode;
	//视频标题
	private String title;
	//videoUrl
	private String videoUrl;
	//columns END
	private String description;

	public ProductVideo(){
	}

	public ProductVideo(
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
	 * productCode
	 * @return
	 */
	public Long getProductCode() {
		return this.productCode;
	}
	
	/**
	 * productCode
	 * @param value
	 */
	public void setProductCode(Long value) {
		this.productCode = value;
	}
	/**
	 * 视频标题
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * 视频标题
	 * @param value
	 */
	public void setTitle(String value) {
		this.title = value;
	}
	/**
	 * videoUrl
	 * @return
	 */
	public String getVideoUrl() {
		return this.videoUrl;
	}
	
	/**
	 * videoUrl
	 * @param value
	 */
	public void setVideoUrl(String value) {
		this.videoUrl = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}

