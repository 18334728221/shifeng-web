package com.xwd.trade.service;

import com.frame.base.BaseService;
import com.xwd.trade.model.Poundage;


public interface PoundageService extends BaseService<Poundage>{

	public int save(Poundage entity);

	public int update(Poundage entity);

	public int saveOrUpdate(Poundage entity);

	public void delete(Poundage entity);
}
