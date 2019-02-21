package com.xwd.trade.provider;

import com.xwd.trade.model.Trade;

/**
 * 交易处理接口
 * @author ljl
 *
 */
public interface TradeProvider {

	/**
	 * 交易记录更新入缓存队列
	 * @param entity
	 */
	public void put(Trade entity);

	/**
	 * 获得一个交易
	 * @return
	 */
	public Trade pop();
	
}
