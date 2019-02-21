package com.xwd.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 持仓redis原子操作类
 * @author ljl
 *
 */
public class CustomerProductBean implements Serializable{

	private static final long serialVersionUID = 1380990264288947295L;
	public static final int OPERATION_INCREASE_TOTAL = 1;
	public static final int OPERATION_DECREASE_TOTAL = 2;
	public static final int OPERATION_INCREASE_SELL_NUM = 3;
	public static final int OPERATION_DECREASE_SELL_NUM = 4;
	public static final int OPERATION_INCREASE_BUY_COST = 5;
	public static final int OPERATION_INCREASE_BUY_POUNDAGE = 6;
	public static final int OPERATION_INCREASE_SELL_POUNDAGE = 7;
	public static final int OPERATION_INCREASE_CASH_FUND = 8;
	
	private int operation = 0;//操作
	private Long customerNo;
	private Long productCode;
	private Long value;
	private BigDecimal cost;
	private String txNo;
	private Boolean isIncrease = false;
	private Long nanoTime = System.nanoTime();
	
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
	
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
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
	
	public Long getProductCode() {
		return productCode;
	}
	public void setProductCode(Long productCode) {
		this.productCode = productCode;
	}
	
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	
	
	public String getKey(){
		return this.customerNo + "" + this.productCode;
	}
	
	public Long getNanoTime() {
		return nanoTime;
	}
	public void setNanoTime(Long nanoTime) {
		this.nanoTime = nanoTime;
	}
	
	
}
