package com.xwd.trade.service;

import com.frame.base.BaseService;
import com.xwd.trade.model.QuarterLine;


public interface QuarterLineService extends BaseService<QuarterLine>{

	public int save(QuarterLine entity);

	public int update(QuarterLine entity);

	public int saveOrUpdate(QuarterLine entity);

	public void delete(QuarterLine entity);
	
	/**
	 * 根据股票编号获得最新的记录
	 * @param params
	 * @return
	 */
	public QuarterLine getLatest(Object ... params);
}
