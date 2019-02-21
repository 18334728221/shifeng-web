package com.xwd.evaluate.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 评价表
 */
public class Evaluate extends BaseEntity {
		
	private static final long serialVersionUID = 6298682597774329070L;
	//columns START
	//id
	private Long id;
	//客户编号
	private Long customerNo;
	//产品ID
	private Long productCode;
	//满意度星级
	private Integer satisfyLevel;
	
	private Integer serveLevel;
	private Integer logisticsLevel;
	//点赞数量
	private Integer thumbsUpNumber;
	//columns END

	public Evaluate(){
	}

	public Evaluate(
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
	 * 客户编号
	 * @return
	 */
	public Long getCustomerNo() {
		return this.customerNo;
	}
	
	/**
	 * 客户编号
	 * @param value
	 */
	public void setCustomerNo(Long value) {
		this.customerNo = value;
	}
	/**
	 * 产品ID
	 * @return
	 */
	public Long getProductCode() {
		return this.productCode;
	}
	
	/**
	 * 产品ID
	 * @param value
	 */
	public void setProductCode(Long value) {
		this.productCode = value;
	}
	
	public Integer getSatisfyLevel() {
		return satisfyLevel;
	}

	public void setSatisfyLevel(Integer satisfyLevel) {
		this.satisfyLevel = satisfyLevel;
	}

	public Integer getServeLevel() {
		return serveLevel;
	}

	public void setServeLevel(Integer serveLevel) {
		this.serveLevel = serveLevel;
	}

	public Integer getLogisticsLevel() {
		return logisticsLevel;
	}

	public void setLogisticsLevel(Integer logisticsLevel) {
		this.logisticsLevel = logisticsLevel;
	}

	/**
	 * 点赞数量
	 * @return
	 */
	public Integer getThumbsUpNumber() {
		return this.thumbsUpNumber;
	}
	
	/**
	 * 点赞数量
	 * @param value
	 */
	public void setThumbsUpNumber(Integer value) {
		this.thumbsUpNumber = value;
	}
	
}

