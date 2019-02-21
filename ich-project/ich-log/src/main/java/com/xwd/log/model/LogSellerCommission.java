package com.xwd.log.model;

import java.math.BigDecimal;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 卖家佣金收入日志
 */
public class LogSellerCommission extends BaseEntity {
		

	private static final long serialVersionUID = -4522261979002075392L;
	//columns START
	//id
	private Long id;
	//顾客编号
	private Long customerNo;
	//产品名称
	private String productName;
	//产品编号
	private Long productCode;
	//交易顾客编号
	private Long tradeCustomerNo;
	//交易顾客名称
	private String tradeCustomerName;
	//交易金额
	private BigDecimal amount;
	//手续费
	private Float rate;
	//收入
	private BigDecimal income;
	//操作时间
	private Long nanoTime;
	//columns END

	public LogSellerCommission(){
	}

	public LogSellerCommission(
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
	 * 顾客编号
	 * @return
	 */
	public Long getCustomerNo() {
		return this.customerNo;
	}
	
	/**
	 * 顾客编号
	 * @param value
	 */
	public void setCustomerNo(Long value) {
		this.customerNo = value;
	}
	/**
	 * 产品名称
	 * @return
	 */
	public String getProductName() {
		return this.productName;
	}
	
	/**
	 * 产品名称
	 * @param value
	 */
	public void setProductName(String value) {
		this.productName = value;
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
	 * 交易顾客编号
	 * @return
	 */
	public Long getTradeCustomerNo() {
		return this.tradeCustomerNo;
	}
	
	/**
	 * 交易顾客编号
	 * @param value
	 */
	public void setTradeCustomerNo(Long value) {
		this.tradeCustomerNo = value;
	}
	/**
	 * 交易顾客名称
	 * @return
	 */
	public String getTradeCustomerName() {
		return this.tradeCustomerName;
	}
	
	/**
	 * 交易顾客名称
	 * @param value
	 */
	public void setTradeCustomerName(String value) {
		this.tradeCustomerName = value;
	}
	/**
	 * 交易金额
	 * @return
	 */
	public BigDecimal getAmount() {
		return this.amount;
	}
	
	/**
	 * 交易金额
	 * @param value
	 */
	public void setAmount(BigDecimal value) {
		this.amount = value;
	}
	/**
	 * 手续费
	 * @return
	 */
	public Float getRate() {
		return this.rate;
	}
	
	/**
	 * 手续费
	 * @param value
	 */
	public void setRate(Float value) {
		this.rate = value;
	}
	/**
	 * 收入
	 * @return
	 */
	public BigDecimal getIncome() {
		return this.income;
	}
	
	/**
	 * 收入
	 * @param value
	 */
	public void setIncome(BigDecimal value) {
		this.income = value;
	}
	/**
	 * 操作时间
	 * @return
	 */
	public Long getNanoTime() {
		return this.nanoTime;
	}
	
	/**
	 * 操作时间
	 * @param value
	 */
	public void setNanoTime(Long value) {
		this.nanoTime = value;
	}
	
}

