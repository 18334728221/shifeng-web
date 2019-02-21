package com.xwd.base.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * EvaluateContent
 */
public class EvaluateContent extends BaseEntity {
		
	private static final long serialVersionUID = -8952671531250441505L;
	//columns START
	//id
	private Long id;
	//content
	private String content;
	//columns END

	public EvaluateContent(){
	}

	public EvaluateContent(
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
	 * content
	 * @return
	 */
	public String getContent() {
		return this.content;
	}
	
	/**
	 * content
	 * @param value
	 */
	public void setContent(String value) {
		this.content = value;
	}
}

