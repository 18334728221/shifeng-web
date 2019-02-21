package com.xwd.product.model;

import java.math.BigDecimal;
import java.util.Calendar;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 发行表
 */
public class ProductIssue extends BaseEntity {
		

	private static final long serialVersionUID = -3508212248050082924L;
	//columns START
	//id
	private Long id;
	//productCode
	private Long productCode;
	//流通盘
	private Long circulatingStock;
	//承销开始时间
	private Calendar underwritingStartTime;
	//承销结束时间
	private Calendar underwritingEndTime;
	//已承销数量
	private Long underwritingTotalAmount;
	//申购开始时间
	private Calendar purchaseStartTime;
	//申购结束时间
	private Calendar purchaseEndTime;
	//申购总数
	private Long purchaseTotalAmount;
	//已申购数量
	private Long purchaseNum;
	//是否申购结束
	private Boolean isPurchaseEnd;
	//流通时间
	private Calendar circulateTime;
	//积分会员可申购股数
	private Long integralPurchaseNum;
	//申购参与积分人数
	private Integer integralNum;
	//申购价格
	private BigDecimal purchasePrice;
	//columns END

	public ProductIssue(){
	}

	public ProductIssue(
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
	 * 流通盘
	 * @return
	 */
	public Long getCirculatingStock() {
		return this.circulatingStock;
	}
	
	/**
	 * 流通盘
	 * @param value
	 */
	public void setCirculatingStock(Long value) {
		this.circulatingStock = value;
	}
	/**
	 * 承销开始时间
	 * @return
	 */
	public String getUnderwritingStartTimeString() {
		return date2String(getUnderwritingStartTime(), DATE_TIME_FORMAT);
	}
	
	/**
	 * 承销开始时间
	 * @param value
	 */
	public void setUnderwritingStartTimeString(String value) {
		setUnderwritingStartTime(string2Date(value, DATE_TIME_FORMAT));
	}
	
	/**
	 * 承销开始时间
	 * @return
	 */
	public Calendar getUnderwritingStartTime() {
		return this.underwritingStartTime;
	}
	
	/**
	 * 承销开始时间
	 * @param value
	 */
	public void setUnderwritingStartTime(Calendar value) {
		this.underwritingStartTime = value;
	}
	/**
	 * 承销结束时间
	 * @return
	 */
	public String getUnderwritingEndTimeString() {
		return date2String(getUnderwritingEndTime(), DATE_TIME_FORMAT);
	}
	
	/**
	 * 承销结束时间
	 * @param value
	 */
	public void setUnderwritingEndTimeString(String value) {
		setUnderwritingEndTime(string2Date(value, DATE_TIME_FORMAT));
	}
	
	/**
	 * 承销结束时间
	 * @return
	 */
	public Calendar getUnderwritingEndTime() {
		return this.underwritingEndTime;
	}
	
	/**
	 * 承销结束时间
	 * @param value
	 */
	public void setUnderwritingEndTime(Calendar value) {
		this.underwritingEndTime = value;
	}
	/**
	 * 已承销数量
	 * @return
	 */
	public Long getUnderwritingTotalAmount() {
		return this.underwritingTotalAmount;
	}
	
	/**
	 * 已承销数量
	 * @param value
	 */
	public void setUnderwritingTotalAmount(Long value) {
		this.underwritingTotalAmount = value;
	}
	/**
	 * 申购开始时间
	 * @return
	 */
	public String getPurchaseStartTimeString() {
		return date2String(getPurchaseStartTime(), DATE_TIME_FORMAT);
	}
	
	/**
	 * 申购开始时间
	 * @param value
	 */
	public void setPurchaseStartTimeString(String value) {
		setPurchaseStartTime(string2Date(value, DATE_TIME_FORMAT));
	}
	
	/**
	 * 申购开始时间
	 * @return
	 */
	public Calendar getPurchaseStartTime() {
		return this.purchaseStartTime;
	}
	
	/**
	 * 申购开始时间
	 * @param value
	 */
	public void setPurchaseStartTime(Calendar value) {
		this.purchaseStartTime = value;
	}
	/**
	 * 申购结束时间
	 * @return
	 */
	public String getPurchaseEndTimeString() {
		return date2String(getPurchaseEndTime(), DATE_TIME_FORMAT);
	}
	
	/**
	 * 申购结束时间
	 * @param value
	 */
	public void setPurchaseEndTimeString(String value) {
		setPurchaseEndTime(string2Date(value, DATE_TIME_FORMAT));
	}
	
	/**
	 * 申购结束时间
	 * @return
	 */
	public Calendar getPurchaseEndTime() {
		return this.purchaseEndTime;
	}
	
	/**
	 * 申购结束时间
	 * @param value
	 */
	public void setPurchaseEndTime(Calendar value) {
		this.purchaseEndTime = value;
	}
	/**
	 * 申购总数
	 * @return
	 */
	public Long getPurchaseTotalAmount() {
		return this.purchaseTotalAmount;
	}
	
	/**
	 * 申购总数
	 * @param value
	 */
	public void setPurchaseTotalAmount(Long value) {
		this.purchaseTotalAmount = value;
	}
	/**
	 * 流通时间
	 * @return
	 */
	public String getCirculateTimeString() {
		return date2String(getCirculateTime(), DATE_TIME_FORMAT);
	}
	
	/**
	 * 流通时间
	 * @param value
	 */
	public void setCirculateTimeString(String value) {
		setCirculateTime(string2Date(value, DATE_TIME_FORMAT));
	}
	
	/**
	 * 流通时间
	 * @return
	 */
	public Calendar getCirculateTime() {
		return this.circulateTime;
	}
	
	/**
	 * 流通时间
	 * @param value
	 */
	public void setCirculateTime(Calendar value) {
		this.circulateTime = value;
	}
	/**
	 * 已申购总数
	 * @return
	 */
	public Long getPurchaseNum() {
		return purchaseNum;
	}

	public void setPurchaseNum(Long purchaseNum) {
		this.purchaseNum = purchaseNum;
	}
	
	public Boolean getIsPurchaseEnd() {
		return isPurchaseEnd;
	}

	public void setIsPurchaseEnd(Boolean isPurchaseEnd) {
		this.isPurchaseEnd = isPurchaseEnd;
	}

	/*
	 * 申购参与积分人数
	 */
	public Integer getIntegralNum() {
		return integralNum;
	}

	public void setIntegralNum(Integer integralNum) {
		this.integralNum = integralNum;
	}

	/**
	 * 申购价格
	 * @return
	 */
	public BigDecimal getPurchasePrice() {
		return this.purchasePrice;
	}
	
	/**
	 * 申购价格
	 * @param value
	 */
	public void setPurchasePrice(BigDecimal value) {
		this.purchasePrice = value;
	}

	public Long getIntegralPurchaseNum() {
		return integralPurchaseNum;
	}

	public void setIntegralPurchaseNum(Long integralPurchaseNum) {
		this.integralPurchaseNum = integralPurchaseNum;
	}
}

