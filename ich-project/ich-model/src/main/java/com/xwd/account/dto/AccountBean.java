package com.xwd.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 账户redis原子操作类
 * @author ljl
 *
 */
public class AccountBean implements Serializable{

	private static final long serialVersionUID = -3379919907327200217L;
	public static final int OPERATION_INCREASE_BALANCE = 1;
	public static final int OPERATION_DECREASE_BALANCE = 2;
	public static final int OPERATION_FROZEN_FUND = 3;
	public static final int OPERATION_UNFROZEN_FUND = 4;
	public static final int OPERATION_INCREASE_BUY_POUNDAGE = 5;
	public static final int OPERATION_INCREASE_SELL_POUNDAGE = 6;
	public static final int OPERATION_INCREASE_COST = 7;
	public static final int OPERATION_DECREASE_COST = 8;
	public static final int OPERATION_SET_COST = 9;
	public static final int OPERATION_INCREASE_MARKET_VALUE = 10;
	public static final int OPERATION_DECREASE_MARKET_VALUE = 11;
	public static final int OPERATION_SET_MARKET_VALUE = 12;
	public static final int OPERATION_INCREASE_WITHDRAW_CASH = 13;
	public static final int OPERATION_DECREASE_WITHDRAW_CASH = 14;
	public static final int OPERATION_PROFIT = 15;
	public static final int OPERATION_LOSS = 16;
	public static final int OPERATION_SET_PROFIT_AND_LOSS = 17;
	public static final int OPERATION_ADD_HISTORY_PROFIT_AND_LOSS = 18;
	
	private int operation = 0;//操作
	private Long customerNo;
	private BigDecimal value;
	private String txNo;
	private Boolean isIncrease = false;
	private Long nanoTime;
	
	public int getOperation() {
		return operation;
	}
	public void setOperation(int operation) {
		this.operation = operation;
	}
	
	public Long getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(Long customerNo) {
		this.customerNo = customerNo;
	}
	
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
	public String getTxNo() {
		return txNo;
	}
	public void setTxNo(String txNo) {
		this.txNo = txNo;
	}
	
	public Boolean getIsIncrease() {
		return isIncrease;
	}
	public void setIsIncrease(Boolean isIncrease) {
		this.isIncrease = isIncrease;
	}
	
	public Long getNanoTime() {
		return nanoTime;
	}
	public void setNanoTime(Long nanoTime) {
		this.nanoTime = nanoTime;
	}
	
}
