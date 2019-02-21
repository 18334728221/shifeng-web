package com.xwd.evaluate.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 嗮图表
 */
public class EvaluateImage extends BaseEntity {
		
	private static final long serialVersionUID = -5808188591358977187L;
	//columns START
	//id
	private Long id;
	//evaluateId
	private Long evaluateId;
	//imageId
	private String imageId;
	//imageServerId
	private Long imageServerId;
	//columns END

	public EvaluateImage(){
	}

	public EvaluateImage(
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
	 * evaluateId
	 * @return
	 */
	public Long getEvaluateId() {
		return this.evaluateId;
	}
	
	/**
	 * evaluateId
	 * @param value
	 */
	public void setEvaluateId(Long value) {
		this.evaluateId = value;
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
}

