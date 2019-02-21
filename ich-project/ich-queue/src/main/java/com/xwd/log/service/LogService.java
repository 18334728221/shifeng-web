package com.xwd.log.service;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

public interface LogService {

	public void add(HttpServletRequest request, String content);
	/**
	 * 查询日志
	 * @param request
	 * @param content
	 */
	public void addQueryLog(HttpServletRequest request, String content);
	
	/**
	 * 账户流水日志
	 * @param customerNo
	 * @param amount
	 * @param sourceType
	 * @param content
	 * @param tradeTime
	 * @param isIncome 是否收入
	 */
	public void addAccountLog(Long customerNo, BigDecimal amount, int sourceType, String content, Calendar tradeTime, boolean isIncome);
	
	/**
	 * 充值日志
	 * @param amount
	 * @param txNo
	 */
	public void addDepositLog(BigDecimal amount, String txNo);
	
	/**
	 * 提现日志
	 * @param amount
	 * @param txNo
	 */
	public void addWithdrawLog(BigDecimal amount, String txNo);
	
}
