package com.xwd.trade.service;

import com.frame.base.BaseService;
import com.xwd.trade.model.MonthLine;


public interface MonthLineService extends BaseService<MonthLine>{

	public int save(MonthLine entity);

	public int update(MonthLine entity);

	public int saveOrUpdate(MonthLine entity);

	public void delete(MonthLine entity);
	
	/**
	 * 根据股票编号获得最新的记录
	 * @param params
	 * @return
	 */
	public MonthLine getLatest(Object ... params);
}
