package com.xwd.log.model;

import java.math.BigDecimal;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 提现日志表
 */
public class LogWithdraws extends BaseEntity {
		
	
	private static final long serialVersionUID = 5114704073038331143L;
	//columns START
	//id
	private Long id;
	//单号
	private String txNo;
	//顾客编号
	private Long customerNo;
	//金额
	private BigDecimal amount;
	//操作人
	private Long userId;
	//操作时间
	private Long nanoTime;
	//columns END

	public LogWithdraws(){
	}

	public LogWithdraws(
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
	 * 单号
	 * @return
	 */
	public String getTxNo() {
		return this.txNo;
	}
	
	/**
	 * 单号
	 * @param value
	 */
	public void setTxNo(String value) {
		this.txNo = value;
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
	 * 金额
	 * @return
	 */
	public BigDecimal getAmount() {
		return this.amount;
	}
	
	/**
	 * 金额
	 * @param value
	 */
	public void setAmount(BigDecimal value) {
		this.amount = value;
	}
	/**
	 * 操作人
	 * @return
	 */
	public Long getUserId() {
		return this.userId;
	}
	
	/**
	 * 操作人
	 * @param value
	 */
	public void setUserId(Long value) {
		this.userId = value;
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

