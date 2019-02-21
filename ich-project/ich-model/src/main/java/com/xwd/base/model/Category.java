package com.xwd.base.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl 产品种类表
 */
public class Category extends BaseEntity implements Comparable<Category>{

	private static final long serialVersionUID = -5242618294740854674L;
	
	// columns START
	// id
	private Long id;
	// 类别名称
	private String name;
	// 描述
	private String description;
	// 序号
	private Integer seq;
	// 图片ID
	private String imageId;
	// 图片服务器ID
	private Long imageServerId;
	// 父类ID
	private Long parentId;
	//是否主推  0:否  1:是
	private Integer isTop;
	private String mainImage;
	// columns END
	private String isTopString;
	
	private String imageUrl;

	public Category() {
	}

	public Category(Long id) {
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
	 * 类别名称
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 类别名称
	 * 
	 * @param value
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * 描述
	 * 
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * 描述
	 * 
	 * @param value
	 */
	public void setDescription(String value) {
		this.description = value;
	}

	/**
	 * 序号
	 * 
	 * @return
	 */
	public Integer getSeq() {
		return this.seq;
	}

	/**
	 * 序号
	 * 
	 * @param value
	 */
	public void setSeq(Integer value) {
		this.seq = value;
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

	/**
	 * 父类ID
	 * 
	 * @return
	 */
	public Long getParentId() {
		return this.parentId;
	}

	/**
	 * 父类ID
	 * 
	 * @param value
	 */
	public void setParentId(Long value) {
		this.parentId = value;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public String getIsTopString() {
		String isTopString = "";
		if(this.isTop != null){
			if(this.isTop == 1){
				isTopString = "是";
			}else{
				isTopString = "否";
			}
		}
		return isTopString;
	}

	public void setIsTopString(String isTopString) {
		this.isTopString = isTopString;
	}

	@Override
	public int compareTo(Category category) {
		if(this.seq > category.getSeq()){
			return 1;
		} else if (this.seq < category.getSeq()){
			return -1;
		} else {
			return 0;
		}
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}
}
