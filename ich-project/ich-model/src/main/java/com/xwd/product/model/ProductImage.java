package com.xwd.product.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl 产品展示图片表
 */
public class ProductImage extends BaseEntity {

	private static final long serialVersionUID = -6862164088804521174L;
	// columns START
	// id
	private Long id;
	// 产品编号
	private Long productCode;
	// 图片ID
	private String imageId;
	// 图片服务器ID
	private Long imageServerId;
	//是否主图
	private Boolean isMain = false;
	// columns END
	private String name;
	
	private String imageUrl;
	public ProductImage() {
	}

	public ProductImage(Long id) {
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
	 * 产品编号
	 * 
	 * @return
	 */
	public Long getProductCode() {
		return this.productCode;
	}

	/**
	 * 产品编号
	 * 
	 * @param value
	 */
	public void setProductCode(Long value) {
		this.productCode = value;
	}

	/**
	 * 图片ID
	 * 
	 * @return
	 */
	public String getImageId() {
		return this.imageId;
	}

	/**
	 * 图片ID
	 * 
	 * @param value
	 */
	public void setImageId(String value) {
		this.imageId = value;
	}

	/**
	 * 图片服务器ID
	 * 
	 * @return
	 */
	public Long getImageServerId() {
		return this.imageServerId;
	}

	/**
	 * 图片服务器ID
	 * 
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

	public Boolean getIsMain() {
		return isMain;
	}

	public void setIsMain(Boolean isMain) {
		this.isMain = isMain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
