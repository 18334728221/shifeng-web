package com.xwd.account.service;

import com.frame.base.BaseService;
import com.xwd.account.model.Account;


public interface AccountService extends BaseService<Account>{

	public int save(Account entity);

	public int update(Account entity);

	public int saveOrUpdate(Account entity);

	public void delete(Account entity);
	
	/**
	 * 更新提现金额
	 * T+n交易的时候 n > 0
	 * @return
	 */
	public int updateWithdrawCash();
	
	/**
	 * 更新缓存
	 * 数据库信息重新加载到缓存
	 */
	public void initCache();
}
