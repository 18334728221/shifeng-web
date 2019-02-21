package com.xwd.account.model;

import java.math.BigDecimal;
import java.util.Calendar;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 充值
 */
public class Deposits extends BaseEntity {
		
	private static final long serialVersionUID = 7833121494437634056L;
	public final static Byte DEPOSITS_FLOW_CREATE = 0;
	public final static Byte DEPOSITS_FLOW_FINISH = 1;
	public final static Byte DEPOSITS_FLOW_CANCEL = 2;
	
	//columns START
	//id
	private Long id;
	//单号
	private String txNo;
	//顾客编号
	private Long customerNo;
	//金额
	private BigDecimal amount;
	//转账银行
	private Long bankId;
	//充值状态（0 等待; 1 完成）
	private Byte flow = 0;
	//结束时间
	private Calendar finishTime;
	//columns END

	public Deposits(){
	}

	public Deposits(
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
	 * 转账银行
	 * @return
	 */
	public Long getBankId() {
		return this.bankId;
	}
	
	/**
	 * 转账银行
	 * @param value
	 */
	public void setBankId(Long value) {
		this.bankId = value;
	}
	/**
	 * 充值状态（0 等待; 1 完成）
	 * @return
	 */
	public Byte getFlow() {
		return this.flow;
	}
	
	/**
	 * 充值状态（0 等待; 1 完成）
	 * @param value
	 */
	public void setFlow(Byte value) {
		this.flow = value;
	}
	/**
	 * 结束时间
	 * @return
	 */
	public String getFinishTimeString() {
		return date2String(getFinishTime(), DATE_TIME_FORMAT);
	}
	
	/**
	 * 结束时间
	 * @param value
	 */
	public void setFinishTimeString(String value) {
		setFinishTime(string2Date(value, DATE_TIME_FORMAT));
	}
	
	/**
	 * 结束时间
	 * @return
	 */
	public Calendar getFinishTime() {
		return this.finishTime;
	}
	
	/**
	 * 结束时间
	 * @param value
	 */
	public void setFinishTime(Calendar value) {
		this.finishTime = value;
	}

}

