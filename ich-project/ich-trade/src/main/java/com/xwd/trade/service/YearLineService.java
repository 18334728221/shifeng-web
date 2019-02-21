package com.xwd.trade.service;

import com.frame.base.BaseService;
import com.xwd.trade.model.YearLine;


public interface YearLineService extends BaseService<YearLine>{

	public int save(YearLine entity);

	public int update(YearLine entity);

	public int saveOrUpdate(YearLine entity);

	public void delete(YearLine entity);
	
	/**
	 * 根据股票编号获得最新的记录
	 * @param params
	 * @return
	 */
	public YearLine getLatest(Object ... params);
}
