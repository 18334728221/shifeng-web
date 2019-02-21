package com.xwd.trade.service;

import com.frame.base.BaseService;
import com.xwd.trade.model.DateLine;


public interface DateLineService extends BaseService<DateLine>{

	public int save(DateLine entity);

	public int update(DateLine entity);

	public int saveOrUpdate(DateLine entity);

	public void delete(DateLine entity);
	
	/**
	 * 根据股票编号获得最新的记录
	 * @param params
	 * @return
	 */
	public DateLine getLatest(Object ... params);
}
