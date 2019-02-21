package com.xwd.trade.provider;

import java.util.List;

import com.xwd.trade.model.TradeOrder;

/**
 * 委托单处理接口
 * @author ljl
 *
 */
public interface TradeOrderProvider {

	public TradeOrder get(Long customerNo, String txNo);
	
	public void save(TradeOrder entity);
	
	public void delete(TradeOrder entity);
	
	public void update(TradeOrder entity);
	
	/**
	 * 根据客户获得委托单
	 * @param customerNo
	 * @return
	 */
	public List<TradeOrder> getByCustomer(Long customerNo);
	
	/**
	 * 放入更新数据库队列
	 * @param entity
	 */
	public void push(TradeOrder entity);
	
	/**
	 * 从缓存队列中获得需要更新数据库的委托单
	 * @return
	 */
	public TradeOrder pop();
	
}
