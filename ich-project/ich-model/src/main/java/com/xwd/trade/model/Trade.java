package com.xwd.trade.model;

import java.math.BigDecimal;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 交易成交记录表
 */
public class Trade extends BaseEntity {
		
	
	private static final long serialVersionUID = -2579455916418827484L;
	
	public final static Byte TRADE_MARK_BUY = 0;
	public final static Byte TRADE_MARK_SELL = 1;
	
	//columns START
	//id
	private Long id;
	//顾客编号
	private Long customerNo;
	//产品名称
	private String productName;
	//产品编号
	private Long productCode;
	//交易单号
	private String tradeNo;
	//买卖标记
	private Byte tradeMark;
	//价格
	private BigDecimal price;
	//成交量
	private Long volume;
	//交易金额
	private BigDecimal amount;
	//手续费
	private BigDecimal poundage;
	//委托号
	private Integer entrustCode;
	//columns END
	
	//总手续费
	private BigDecimal totalPoundage;
	
	public Trade(){
	}

	public Trade(
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
	 * productName
	 * @return
	 */
	public String getProductName() {
		return this.productName;
	}
	
	/**
	 * productName
	 * @param value
	 */
	public void setProductName(String value) {
		this.productName = value;
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
	 * 交易单号
	 * @return
	 */
	public String getTradeNo() {
		return this.tradeNo;
	}
	
	/**
	 * 交易单号
	 * @param value
	 */
	public void setTradeNo(String value) {
		this.tradeNo = value;
	}
	
	/**
	 * 买卖标记
	 * @return
	 */
	public Byte getTradeMark() {
		return this.tradeMark;
	}
	
	/**
	 * 买卖标记
	 * @param value
	 */
	public void setTradeMark(Byte value) {
		this.tradeMark = value;
	}
	/**
	 * 价格
	 * @return
	 */
	public BigDecimal getPrice() {
		return this.price;
	}
	
	/**
	 * 价格
	 * @param value
	 */
	public void setPrice(BigDecimal value) {
		this.price = value;
	}
	/**
	 * 成交量
	 * @return
	 */
	public Long getVolume() {
		return this.volume;
	}
	
	/**
	 * 成交量
	 * @param value
	 */
	public void setVolume(Long value) {
		this.volume = value;
	}
	/**
	 * amount
	 * @return
	 */
	public BigDecimal getAmount() {
		return this.amount;
	}
	
	/**
	 * amount
	 * @param value
	 */
	public void setAmount(BigDecimal value) {
		this.amount = value;
	}
	
	public BigDecimal getPoundage() {
		return poundage;
	}

	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
	}

	/**
	 * 委托号
	 * @return
	 */
	public Integer getEntrustCode() {
		return this.entrustCode;
	}
	
	/**
	 * 委托号
	 * @param value
	 */
	public void setEntrustCode(Integer value) {
		this.entrustCode = value;
	}
	
	/**
	 * 计算
	 * 成交总金额
	 * 手续费
	 * @param poundage
	 */
	public void calculate(BigDecimal poundage){
		amount = this.price.multiply(new BigDecimal(this.volume));
		BigDecimal c = amount.multiply(poundage);
		poundage = c.divide(new BigDecimal(1000), 2, BigDecimal.ROUND_CEILING);
	}

	public BigDecimal getTotalPoundage() {
		return totalPoundage;
	}

	public void setTotalPoundage(BigDecimal totalPoundage) {
		System.out.println("setTotalPoundage>>>>>" + totalPoundage);
		this.totalPoundage = totalPoundage;
	}
	
}

