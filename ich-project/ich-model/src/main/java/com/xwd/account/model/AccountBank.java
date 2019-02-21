package com.xwd.account.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 银行账户表
 */
public class AccountBank extends BaseEntity {
		
	private static final long serialVersionUID = -2375769305848120694L;
	//columns START
	//id
	private Long id;
	//顾客编号
	private Long customerNo;
	//姓名
	private String name;
	//银行账户
	private String bankAccount;
	//银行
	private String bank;
	//支行
	private String branchBank;
	//columns END

	public AccountBank(){
	}

	public AccountBank(
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 银行账户
	 * @return
	 */
	public String getBankAccount() {
		return this.bankAccount;
	}
	
	/**
	 * 银行账户
	 * @param value
	 */
	public void setBankAccount(String value) {
		this.bankAccount = value;
	}
	/**
	 * 银行
	 * @return
	 */
	public String getBank() {
		return this.bank;
	}
	
	/**
	 * 银行
	 * @param value
	 */
	public void setBank(String value) {
		this.bank = value;
	}
	/**
	 * 支行
	 * @return
	 */
	public String getBranchBank() {
		return this.branchBank;
	}
	
	/**
	 * 支行
	 * @param value
	 */
	public void setBranchBank(String value) {
		this.branchBank = value;
	}
}

