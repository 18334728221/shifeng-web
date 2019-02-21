package com.xwd.log.model;

import java.math.BigDecimal;
import java.util.Calendar;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 充值日志表
 */
public class LogDeposits extends BaseEntity {
		

	private static final long serialVersionUID = 5936465325385549095L;
	//columns START
	//id
	private Long id;
	//顾客编号
	private Long customerNo;
	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	//充值单号
	private String txNo;
	//操作人
	private Long userId;
	//金额
	private BigDecimal amount;
	//操作时间
	private Long nanoTime;
	
	//创建时间
	private Calendar createTime;
	//columns END

	public LogDeposits(){
	}

	public LogDeposits(
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
	 * 充值单号
	 * @return
	 */
	public String getTxNo() {
		return this.txNo;
	}
	
	/**
	 * 充值单号
	 * @param value
	 */
	public void setTxNo(String value) {
		this.txNo = value;
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

