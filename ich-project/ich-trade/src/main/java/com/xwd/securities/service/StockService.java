package com.xwd.securities.service;

import com.frame.base.BaseService;
import com.xwd.securities.model.Stock;


public interface StockService extends BaseService<Stock>{

	public int save(Stock entity);

	public int update(Stock entity);

	public int saveOrUpdate(Stock entity);

	public void delete(Stock entity);
	
	public void initCache();
}
