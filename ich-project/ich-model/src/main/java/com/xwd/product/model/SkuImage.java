package com.xwd.product.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 产品展示图片表
 */
public class SkuImage extends BaseEntity {
		
	private static final long serialVersionUID = 8540928740232382005L;
	//columns START
	//id
	private Long id;
	//产品编号
	private Long productCode;
	//productSkuId
	private Long productSkuId;
	//图片ID
	private String imageId;
	//图片服务器ID
	private Long imageServerId;
	//columns END
	
	private String imageUrl;
	
	private String productName;

	public SkuImage(){
	}

	public SkuImage(
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
	 * productSkuId
	 * @return
	 */
	public Long getProductSkuId() {
		return this.productSkuId;
	}
	
	/**
	 * productSkuId
	 * @param value
	 */
	public void setProductSkuId(Long value) {
		this.productSkuId = value;
	}
	/**
	 * 图片ID
	 * @return
	 */
	public String getImageId() {
		return this.imageId;
	}
	
	/**
	 * 图片ID
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}

