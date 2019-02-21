package com.xwd.account.service;

import com.frame.base.BaseService;
import com.xwd.account.model.AccountBank;


public interface AccountBankService extends BaseService<AccountBank>{

	public int save(AccountBank entity);

	public int update(AccountBank entity);

	public int saveOrUpdate(AccountBank entity);

	public void delete(AccountBank entity);
}
