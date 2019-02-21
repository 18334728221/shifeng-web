package com.xwd.dd.service;

import com.frame.base.BaseService;
import com.frame.page.Page;
import com.xwd.base.model.Bank;


public interface BankService extends BaseService<Bank>{

	public int save(Bank entity);

	public int update(Bank entity);

	public int saveOrUpdate(Bank entity);

	public void delete(Bank entity);
}
