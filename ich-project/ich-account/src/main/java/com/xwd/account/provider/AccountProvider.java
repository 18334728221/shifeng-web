package com.xwd.account.provider;

import java.math.BigDecimal;

import com.xwd.account.dto.AccountBean;
import com.xwd.account.model.Account;


/**
 * 账户访问redis接口
 * @author ljl
 *
 */
public interface AccountProvider {

	/**
	 * 保存账户到缓存
	 * @param entity
	 */
	public void save(Account entity);
	
	/**
	 * 更新账户到缓存
	 * @param entity
	 */
	public void update(Account entity, boolean isUpdateDatabase);
	
	/**
	 * 获得账号信息，有延迟
	 * @param customerNo
	 * @return
	 */
	public Account get(Long customerNo);
	
	/**
	 * 获得当前账号信息，没有延迟
	 * @param customerNo
	 * @return
	 */
	public Account getCurrentAccount(Long customerNo);
	
	/**
	 * 增加资金余额
	 * @param customerNo
	 * @param amount
	 */
	public void increaseBalance(String txNo, Long customerNo, BigDecimal amount);
	
	/**
	 * 减少资金余额
	 * @param customerNo
	 * @param amount
	 */
	public void decreaseBalance(String txNo, Long customerNo, BigDecimal amount);
	
	/**
	 * 冻结资金
	 * @param customerNo
	 * @param amount
	 */
	public void frozenFund(Long customerNo, BigDecimal amount);
	
	/**
	 * 解冻资金
	 * 主要买入的股票分批成交
	 * @param customerNo
	 * @param amount
	 */
	public void unfrozenFund(Long customerNo, BigDecimal amount);
	
	/**
	 * 增加买的手续费
	 * @param customerNo
	 * @param amount
	 */
	public void increaseBuyPoundage(Long customerNo, BigDecimal amount);
	
	/**
	 * 增加卖的手续费
	 * @param customerNo
	 * @param amount
	 */
	public void increaseSellPoundage(Long customerNo, BigDecimal amount);
	
	/**
	 * 增加成本
	 * @param customerNo
	 * @param amount
	 */
	public void increaseCost(Long customerNo, BigDecimal amount);
	
	/**
	 * 减少成本
	 * @param customerNo
	 * @param amount
	 */
	public void decreaseCost(Long customerNo, BigDecimal amount);
	
	/**
	 * 设置成本
	 * @param customerNo
	 * @param amount
	 */
	public void setCost(Long customerNo, BigDecimal amount);
	
	/**
	 * 增加总市值
	 * @param customerNo
	 * @param amount
	 */
	public void increaseMarketValue(Long customerNo, BigDecimal amount);
	
	/**
	 * 减少总市值
	 * @param customerNo
	 * @param amount
	 */
	public void decreaseMarketValue(Long customerNo, BigDecimal amount);
	
	/**
	 * 设置总市值
	 * @param customerNo
	 * @param amount
	 */
	public void setMarketValue(Long customerNo, BigDecimal amount);
	
	/**
	 * 增加可提现资金
	 * @param customerNo
	 * @param amount
	 */
	public void increaseWithdrawCash(Long customerNo, BigDecimal amount);
	
	/**
	 * 减少可提现资金
	 * @param customerNo
	 * @param amount
	 */
	public void decreaseWithdrawCash(Long customerNo, BigDecimal amount);
	
	/**
	 * 增加盈利
	 * @param customerNo
	 * @param amount
	 */
	public void profit(Long customerNo, BigDecimal amount);
	
	/**
	 * 亏损
	 * @param customerNo
	 * @param amount
	 */
	public void loss(Long customerNo, BigDecimal amount);
	
	/**
	 * 设置盈亏
	 * @param customerNo
	 * @param amount
	 */
	public void setProfitAndLoss(Long customerNo, BigDecimal amount);
  
	/**
	 * + 出清的盈亏
	 * @param customerNo
	 * @param amount
	 */
	public void addHistoryProfitAndLoss(Long customerNo, BigDecimal amount);
	
	/**
	 * 获得一个AccountBean
	 * @return null表示缓存中没有
	 */
	public AccountBean pop();
	
	/**
	 * 删除临时缓存信息
	 * @param key
	 * @param txNo
	 */
	public void delete(String key, String txNo);
}
