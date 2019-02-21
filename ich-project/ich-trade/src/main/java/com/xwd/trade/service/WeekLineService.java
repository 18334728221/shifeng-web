package com.xwd.trade.service;

import com.frame.base.BaseService;
import com.xwd.trade.model.WeekLine;


public interface WeekLineService extends BaseService<WeekLine>{

	public int save(WeekLine entity);

	public int update(WeekLine entity);

	public int saveOrUpdate(WeekLine entity);

	public void delete(WeekLine entity);
	
	/**
	 * 根据股票编号获得最新的记录
	 * @param params
	 * @return
	 */
	public WeekLine getLatest(Object ... params);
}
