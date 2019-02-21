package com.xwd.seller.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 手艺人和产品分类关系表
 */
public class CraftsmanCategory extends BaseEntity {
		
	private static final long serialVersionUID = 4910828579446987719L;
	//columns START
	//id
	private Long id;
	//craftsmanNo
	private Long craftsmanNo;
	//categoryId
	private Long categoryId;
	//columns END

	public CraftsmanCategory(){
	}

	public CraftsmanCategory(
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
	 * craftsmanNo
	 * @return
	 */
	public Long getCraftsmanNo() {
		return this.craftsmanNo;
	}
	
	/**
	 * craftsmanNo
	 * @param value
	 */
	public void setCraftsmanNo(Long value) {
		this.craftsmanNo = value;
	}
	/**
	 * categoryId
	 * @return
	 */
	public Long getCategoryId() {
		return this.categoryId;
	}
	
	/**
	 * categoryId
	 * @param value
	 */
	public void setCategoryId(Long value) {
		this.categoryId = value;
	}
}

