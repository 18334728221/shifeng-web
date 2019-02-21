package com.xwd.account.model;

import java.math.BigDecimal;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 顾客账户表
 */
public class Account extends BaseEntity {
		
	private static final long serialVersionUID = -606286887323213918L;
	//columns START
	//id
	private Long id;
	//顾客编号
	private Long customerNo;
	//余额
	private BigDecimal balance;
	//可提现资金
	private BigDecimal withdrawCash;
	//冻结资金
	private BigDecimal blockedFunds;
	//总市值
	private BigDecimal marketValue;
	//总成本
	private BigDecimal cost;
	//盈亏
	private BigDecimal profitAndLoss;
	//历史总盈亏,出清的累计
	private BigDecimal historyProfitAndLoss;
	//买手续费
	private BigDecimal buyPoundage;
	//卖手续费
	private BigDecimal sellPoundage;
	//columns END
		
	public Account(){
	}

	public Account(
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
	 * 余额
	 * @return
	 */
	public BigDecimal getBalance() {
		return this.balance;
	}
	
	/**
	 * 余额
	 * @param value
	 */
	public void setBalance(BigDecimal value) {
		this.balance = value;
	}
	/**
	 * 可提现资金
	 * @return
	 */
	public BigDecimal getWithdrawCash() {
		return this.withdrawCash;
	}
	
	/**
	 * 可提现资金
	 * @param value
	 */
	public void setWithdrawCash(BigDecimal value) {
		this.withdrawCash = value;
	}
	/**
	 * 冻结资金
	 * @return
	 */
	public BigDecimal getBlockedFunds() {
		return this.blockedFunds;
	}
	
	/**
	 * 冻结资金
	 * @param value
	 */
	public void setBlockedFunds(BigDecimal value) {
		this.blockedFunds = value;
	}
	/**
	 * 总市值
	 * @return
	 */
	public BigDecimal getMarketValue() {
		return this.marketValue;
	}
	
	/**
	 * 总市值
	 * @param value
	 */
	public void setMarketValue(BigDecimal value) {
		this.marketValue = value;
	}
	/**
	 * 总成本
	 * @return
	 */
	public BigDecimal getCost() {
		return this.cost;
	}
	
	/**
	 * 总成本
	 * @param value
	 */
	public void setCost(BigDecimal value) {
		this.cost = value;
	}
	/**
	 * 盈亏
	 * @return
	 */
	public BigDecimal getProfitAndLoss() {
		return this.profitAndLoss;
	}
	
	/**
	 * 盈亏
	 * @param value
	 */
	public void setProfitAndLoss(BigDecimal value) {
		this.profitAndLoss = value;
	}
	
	public BigDecimal getHistoryProfitAndLoss() {
		return historyProfitAndLoss;
	}

	public void setHistoryProfitAndLoss(BigDecimal historyProfitAndLoss) {
		this.historyProfitAndLoss = historyProfitAndLoss;
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
	 * 累计总盈亏=历史总盈亏 + 盈亏
	 * @return
	 */
	public BigDecimal getTotalProfitAndLoss() {
		return this.historyProfitAndLoss.add(this.profitAndLoss);
	}
	
	
	public void print(){
		System.out.println("----------------账户信息-------------------");
		System.out.println("balance>>>>>" + this.balance);
		System.out.println("blockedFunds>>>>>" + this.blockedFunds);
		System.out.println("cost>>>>>" + this.cost);
		System.out.println("withdrawCash>>>>>" + this.withdrawCash);
		System.out.println("buyPoundage>>>>>" + this.buyPoundage);
		System.out.println("sellPoundage>>>>>" + this.sellPoundage);
	}
}

