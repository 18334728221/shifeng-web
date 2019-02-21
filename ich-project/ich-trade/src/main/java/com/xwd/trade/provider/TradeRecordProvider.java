package com.xwd.trade.provider;

import com.xwd.engine.model.TradeRecord;

/**
 * 撮合引擎返回数据缓存接口
 * @author ljl
 *
 */
public interface TradeRecordProvider {

	public void push(TradeRecord entity);
	
	public TradeRecord pop();
}
