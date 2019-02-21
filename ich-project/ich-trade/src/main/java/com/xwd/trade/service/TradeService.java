package com.xwd.trade.service;

import java.util.ArrayList;

import com.frame.base.BaseService;
import com.xwd.trade.model.Trade;


public interface TradeService extends BaseService<Trade>{

	public int save(Trade entity);

	public int update(Trade entity);

	public int saveOrUpdate(Trade entity);

	public void delete(Trade entity);
	
	public int saveBatch(ArrayList<Trade> list);
	
	/**
	 * 查询上个月到今天的交易
	 * @param paras
	 * @return
	 */
	public Trade findByMonth(Object... paras);
	/**
	 * 卖出的手续费
	 * @param paras
	 * @return
	 */
	public Trade findSellPoundage(Object... paras);
}
