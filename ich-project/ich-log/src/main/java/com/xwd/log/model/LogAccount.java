package com.xwd.log.model;

import java.math.BigDecimal;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 账户流水
 */
public class LogAccount extends BaseEntity {
		
	public final static int LOGACCOUNT_SOURCETYPE_DEPOSITS = 1;//充值
	public final static int LOGACCOUNT_SOURCETYPE_WITHDRAW = 2;//提现
	public final static int LOGACCOUNT_SOURCETYPE_TRADE = 3;//交易
	
	private static final long serialVersionUID = 7670031863060557730L;
	//columns START
	//id
	private Long id;
	//用户唯一标识
	private Long customerNo;
	//来源 1:充值 2:提现 3:交易
	private int sourceType;
	//收入
	private BigDecimal income;
	//支出
	private BigDecimal expenses;
	//columns END

	public LogAccount(){
	}

	public LogAccount(
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
	 * 用户唯一标识
	 * @return
	 */
	public Long getCustomerNo() {
		return this.customerNo;
	}
	
	/**
	 * 用户唯一标识
	 * @param value
	 */
	public void setCustomerNo(Long value) {
		this.customerNo = value;
	}
	/**
	 * 来源 1:充值 2:提现 3:交易
	 * @return
	 */
	public int getSourceType() {
		return this.sourceType;
	}
	
	/**
	 * 来源 1:充值 2:提现 3:交易
	 * @param value
	 */
	public void setSourceType(int value) {
		this.sourceType = value;
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
	 * 支出
	 * @return
	 */
	public BigDecimal getExpenses() {
		return this.expenses;
	}
	
	/**
	 * 支出
	 * @param value
	 */
	public void setExpenses(BigDecimal value) {
		this.expenses = value;
	}
	
}

