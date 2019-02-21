package com.xwd.trade.service;

import com.frame.base.BaseService;
import com.xwd.trade.model.TradePickUp;


public interface TradePickUpService extends BaseService<TradePickUp>{

	public int save(TradePickUp entity);

	public int update(TradePickUp entity);

	public int saveOrUpdate(TradePickUp entity);

	public void delete(TradePickUp entity);
	
	public Long findPickUpNum(Object ...params);
}
