package com.xwd.trade.model;

import java.math.BigDecimal;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 手续费
 */
public class Poundage extends BaseEntity {
		

	private static final long serialVersionUID = 1910542772108334134L;
	//columns START
	//id
	private Long id;
	//用户ID
	private Long customerNo;
	//月份 格式为201705
	private String months;
	//交易总额
	private BigDecimal amount = new BigDecimal(0);
	//总手续费
	private BigDecimal poundage = new BigDecimal(0);
	//卖手续费
	private BigDecimal sellPoundage = new BigDecimal(0);
	//买手续费
	private BigDecimal buyPoundage = new BigDecimal(0);
	//columns END

	public Poundage(){
	}

	public Poundage(
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
	 * 用户ID
	 * @return
	 */
	public Long getCustomerNo() {
		return this.customerNo;
	}
	
	/**
	 * 用户ID
	 * @param value
	 */
	public void setCustomerNo(Long value) {
		this.customerNo = value;
	}
	/**
	 * 月份 格式为201705
	 * @return
	 */
	public String getMonths() {
		return this.months;
	}
	
	/**
	 * 月份 格式为201705
	 * @param value
	 */
	public void setMonths(String value) {
		this.months = value;
	}
	/**
	 * 交易总额
	 * @return
	 */
	public BigDecimal getAmount() {
		return this.amount;
	}
	
	/**
	 * 交易总额
	 * @param value
	 */
	public void setAmount(BigDecimal value) {
		this.amount = value;
	}
	/**
	 * 总手续费
	 * @return
	 */
	public BigDecimal getPoundage() {
		return this.poundage;
	}
	
	/**
	 * 总手续费
	 * @param value
	 */
	public void setPoundage(BigDecimal value) {
		this.poundage = value;
	}
	/**
	 * 卖手续费
	 * @return
	 */
	public BigDecimal getSellPoundage() {
		return this.sellPoundage;
	}
	
	/**
	 * 卖手续费
	 * @param value
	 */
	public void setSellPoundage(BigDecimal value) {
		this.sellPoundage = value;
	}
	/**
	 * 买手续费
	 * @return
	 */
	public BigDecimal getBuyPoundage() {
		return this.buyPoundage;
	}
	
	/**
	 * 买手续费
	 * @param value
	 */
	public void setBuyPoundage(BigDecimal value) {
		this.buyPoundage = value;
	}
}

