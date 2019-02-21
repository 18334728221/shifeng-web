package com.xwd.trade.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 股票指数
 * @author ljl
 *
 */
public class StockIndex implements Serializable {

	private static final long serialVersionUID = -1504855215095539735L;

	// 现价
	private BigDecimal currentPrice;
	// 涨幅
	private Double rose;
	// 开盘价
	private BigDecimal openingPrice;
	// 最高价
	private BigDecimal highestPrice;
	// 最低价
	private BigDecimal minimumPrice;
	// 总量
	private Long total;
	// 现量
	private Integer pratyaksa;
	// 均价
	private BigDecimal avgPrice;
	// 金额
	private BigDecimal amount;
	// 外盘
	private Long outside;
	// 内盘
	private Long inside;
	// 库存量
	private Long inventory;

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getRoseString() {
		DecimalFormat myformat= (DecimalFormat)NumberFormat.getPercentInstance(); 
		myformat.applyPattern("0%"); //0表示加的小数点,00表示两位小数点，你用00试一下你就知道效果 
		myformat.setMaximumFractionDigits(2);//这个1的意识是保存结果到小数点后几位（精确度） 
		return myformat.format(rose); 
	}
	
	public Double getRost(){
		return rose;
	}

	public void setRose(Double rose) {
		this.rose = rose;
	}

	public BigDecimal getOpeningPrice() {
		return openingPrice;
	}

	public void setOpeningPrice(BigDecimal openingPrice) {
		this.openingPrice = openingPrice;
	}

	public BigDecimal getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(BigDecimal highestPrice) {
		this.highestPrice = highestPrice;
	}

	public BigDecimal getMinimumPrice() {
		return minimumPrice;
	}

	public void setMinimumPrice(BigDecimal minimumPrice) {
		this.minimumPrice = minimumPrice;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Integer getPratyaksa() {
		return pratyaksa;
	}

	public void setPratyaksa(Integer pratyaksa) {
		this.pratyaksa = pratyaksa;
	}

	public BigDecimal getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(BigDecimal avgPrice) {
		this.avgPrice = avgPrice;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getOutside() {
		return outside;
	}

	public void setOutside(Long outside) {
		this.outside = outside;
	}

	public Long getInside() {
		return inside;
	}

	public void setInside(Long inside) {
		this.inside = inside;
	}

	public Long getInventory() {
		return inventory;
	}

	public void setInventory(Long inventory) {
		this.inventory = inventory;
	}
	
	

}
