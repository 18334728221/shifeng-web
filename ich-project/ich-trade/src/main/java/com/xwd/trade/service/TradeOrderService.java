package com.xwd.trade.service;

import java.util.ArrayList;
import java.util.List;

import com.frame.base.BaseService;
import com.xwd.trade.model.TradeOrder;


public interface TradeOrderService extends BaseService<TradeOrder>{

	public int save(TradeOrder entity);

	public int update(TradeOrder entity);

	public int saveOrUpdate(TradeOrder entity);

	public void delete(TradeOrder entity);
	
	public int saveBatch(ArrayList<TradeOrder> list);
	
	/**
	 * 交易
	 * @param entity
	 */
	public void trade(TradeOrder entity);
	
	public  List<TradeOrder> findSuccess(Object ...params);
	
	public void cancel(TradeOrder entity);
	
	/**
	 * 撤单的时候批量更新
	 * @param params
	 * @return
	 */
	public int updateAsCancel(Object... params);
	
}
