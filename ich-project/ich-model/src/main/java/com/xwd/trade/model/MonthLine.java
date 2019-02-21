package com.xwd.trade.model;

import java.math.BigDecimal;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 月线
 */
public class MonthLine extends BaseEntity {
		
	private static final long serialVersionUID = 7847584103787648295L;
	//columns START
	//id
	private Long id;
	//产品编号
	private Long productCode;
	//开盘价
	private BigDecimal openingPrice;
	//收盘价
	private BigDecimal closingPrice;
	//最高价
	private BigDecimal highestPrice;
	//最低价
	private BigDecimal lowestPrice;
	//交易时间
	private String tradeTime;
	//成交总金额
	private BigDecimal totalAmount;
	//成交量
	private Long volume;
	//涨跌幅
	private Float priceFluctuation;
	//换手率
	private Float turnoverRate;
	//涨跌价格
	private BigDecimal changePrice;
	//columns END

	public MonthLine(){
	}

	public MonthLine(
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
	 * 开盘价
	 * @return
	 */
	public BigDecimal getOpeningPrice() {
		return this.openingPrice;
	}
	
	/**
	 * 开盘价
	 * @param value
	 */
	public void setOpeningPrice(BigDecimal value) {
		this.openingPrice = value;
	}
	/**
	 * 收盘价
	 * @return
	 */
	public BigDecimal getClosingPrice() {
		return this.closingPrice;
	}
	
	/**
	 * 收盘价
	 * @param value
	 */
	public void setClosingPrice(BigDecimal value) {
		this.closingPrice = value;
	}
	/**
	 * 最高价
	 * @return
	 */
	public BigDecimal getHighestPrice() {
		return this.highestPrice;
	}
	
	/**
	 * 最高价
	 * @param value
	 */
	public void setHighestPrice(BigDecimal value) {
		this.highestPrice = value;
	}
	/**
	 * 最低价
	 * @return
	 */
	public BigDecimal getLowestPrice() {
		return this.lowestPrice;
	}
	
	/**
	 * 最低价
	 * @param value
	 */
	public void setLowestPrice(BigDecimal value) {
		this.lowestPrice = value;
	}
	/**
	 * 交易时间
	 * @return
	 */
	public String getTradeTime() {
		return this.tradeTime;
	}
	
	/**
	 * 交易时间
	 * @param value
	 */
	public void setTradeTime(String value) {
		this.tradeTime = value;
	}
	/**
	 * 成交总金额
	 * @return
	 */
	public BigDecimal getTotalAmount() {
		return this.totalAmount;
	}
	
	/**
	 * 成交总金额
	 * @param value
	 */
	public void setTotalAmount(BigDecimal value) {
		this.totalAmount = value;
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
	 * 涨跌幅
	 * @return
	 */
	public Float getPriceFluctuation() {
		return this.priceFluctuation;
	}
	
	/**
	 * 涨跌幅
	 * @param value
	 */
	public void setPriceFluctuation(Float value) {
		this.priceFluctuation = value;
	}
	/**
	 * 换手率
	 * @return
	 */
	public Float getTurnoverRate() {
		return this.turnoverRate;
	}
	
	/**
	 * 换手率
	 * @param value
	 */
	public void setTurnoverRate(Float value) {
		this.turnoverRate = value;
	}
	/**
	 * 涨跌价格
	 * @return
	 */
	public BigDecimal getChangePrice() {
		return this.changePrice;
	}
	
	/**
	 * 涨跌价格
	 * @param value
	 */
	public void setChangePrice(BigDecimal value) {
		this.changePrice = value;
	}
}

