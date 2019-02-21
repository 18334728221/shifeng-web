package com.xwd.account.service;

import com.frame.base.BaseService;
import com.xwd.account.model.Deposits;


public interface DepositsService extends BaseService<Deposits>{

	public int save(Deposits entity);

	public int update(Deposits entity);

	public int saveOrUpdate(Deposits entity);

	public void delete(Deposits entity);
}
