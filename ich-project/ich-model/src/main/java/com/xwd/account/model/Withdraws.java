package com.xwd.account.model;

import java.math.BigDecimal;
import java.util.Calendar;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 提现
 */
public class Withdraws extends BaseEntity {
		
	//columns START
	//id
	private Long id;
	//单号
	private String txNo;
	//客户编号
	private Long customerNo;
	//提现金额
	private BigDecimal amount;
	//手续费
	private BigDecimal poundage;
	//实际提现金额
	private BigDecimal actualAmount;
	//银行账户
	private Long bankId;
	//提现状态
	private Byte flow;
	//完成时间
	private Calendar finishTime;
	//columns END

	public Withdraws(){
	}

	public Withdraws(
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
	 * 客户编号
	 * @return
	 */
	public Long getCustomerNo() {
		return this.customerNo;
	}
	
	/**
	 * 客户编号
	 * @param value
	 */
	public void setCustomerNo(Long value) {
		this.customerNo = value;
	}
	/**
	 * 提现金额
	 * @return
	 */
	public BigDecimal getAmount() {
		return this.amount;
	}
	
	/**
	 * 提现金额
	 * @param value
	 */
	public void setAmount(BigDecimal value) {
		this.amount = value;
	}
	/**
	 * 手续费
	 * @return
	 */
	public BigDecimal getPoundage() {
		return this.poundage;
	}
	
	/**
	 * 手续费
	 * @param value
	 */
	public void setPoundage(BigDecimal value) {
		this.poundage = value;
	}
	/**
	 * 实际提现金额
	 * @return
	 */
	public BigDecimal getActualAmount() {
		return this.actualAmount;
	}
	
	/**
	 * 实际提现金额
	 * @param value
	 */
	public void setActualAmount(BigDecimal value) {
		this.actualAmount = value;
	}
	/**
	 * 银行账户
	 * @return
	 */
	public Long getBankId() {
		return this.bankId;
	}
	
	/**
	 * 银行账户
	 * @param value
	 */
	public void setBankId(Long value) {
		this.bankId = value;
	}
	/**
	 * 提现状态
	 * @return
	 */
	public Byte getFlow() {
		return this.flow;
	}
	
	/**
	 * 提现状态
	 * @param value
	 */
	public void setFlow(Byte value) {
		this.flow = value;
	}
	/**
	 * 完成时间
	 * @return
	 */
	public String getFinishTimeString() {
		return date2String(getFinishTime(), DATE_TIME_FORMAT);
	}
	
	/**
	 * 完成时间
	 * @param value
	 */
	public void setFinishTimeString(String value) {
		setFinishTime(string2Date(value, DATE_TIME_FORMAT));
	}
	
	/**
	 * 完成时间
	 * @return
	 */
	public Calendar getFinishTime() {
		return this.finishTime;
	}
	
	/**
	 * 完成时间
	 * @param value
	 */
	public void setFinishTime(Calendar value) {
		this.finishTime = value;
	}
}

