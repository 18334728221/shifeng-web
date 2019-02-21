package com.xwd.engine.service;

import java.util.List;

import com.xwd.engine.model.MarketOrder;
import com.xwd.engine.model.Order;
import com.xwd.engine.model.TradeRecord;

/**
 * 引擎缓存之类交易接口
 * @author ljl
 *
 */
public interface TradeService {

	/**
	 * 获得对应股票前n位的买卖单
	 * @param 股票编码
	 * @param n
	 */
	public MarketOrder getMarketOrder(Long code, Integer n);
	
	/**
	 * 发送交易单
	 * @param order
	 */
	public void trade(Order order);
	
	/**
	 * 发送交易单
	 * @param order
	 */
	public void cancel(Order order);
	
	/**
	 * 根据券商获得交易记录
	 * 一次返回100条数据
	 * @param broker
	 * @return
	 */
	public List<TradeRecord> getTradeRecords(String broker);
}
