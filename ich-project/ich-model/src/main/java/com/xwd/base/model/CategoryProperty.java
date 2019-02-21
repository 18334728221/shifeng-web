package com.xwd.base.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 分类属性表
 */
public class CategoryProperty extends BaseEntity {
		
	private static final long serialVersionUID = -4914451360505037232L;
	//columns START
	//id
	private Long id;
	//categoryId
	private Long categoryId;
	//属性
	private String name;
	//属性值
	private String value;
	//columns END
	private String categoryName;

	public CategoryProperty(){
	}

	public CategoryProperty(
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
	/**
	 * 属性
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * 属性
	 * @param value
	 */
	public void setName(String value) {
		this.name = value;
	}
	/**
	 * 属性值
	 * @return
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * 属性值
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}

