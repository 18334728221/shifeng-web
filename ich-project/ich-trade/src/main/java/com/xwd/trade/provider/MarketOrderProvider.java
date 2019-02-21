package com.xwd.trade.provider;

import com.xwd.engine.model.MarketOrder;

/**
 * 挂卖单处理接口
 * @author ljl
 *
 */
public interface MarketOrderProvider {

	public void saveOrUpdate(Long code, MarketOrder entity);
	
	public MarketOrder get(Long code);
	
}
