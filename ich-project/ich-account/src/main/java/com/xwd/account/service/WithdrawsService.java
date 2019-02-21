package com.xwd.account.service;

import com.frame.base.BaseService;
import com.xwd.account.model.Withdraws;


public interface WithdrawsService extends BaseService<Withdraws>{

	public int save(Withdraws entity);

	public int update(Withdraws entity);

	public int saveOrUpdate(Withdraws entity);

	public void delete(Withdraws entity);
}
