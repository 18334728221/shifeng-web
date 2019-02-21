package com.xwd.trade.provider.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.xwd.engine.model.MarketOrder;
import com.xwd.trade.provider.MarketOrderProvider;

/**
 * 挂卖单处理实现类
 * @author ljl
 *
 */
@Service
public class MarketOrderProviderImpl implements MarketOrderProvider{

	private final static String MARKET_ORDER_KEY = "market_order";
	@Autowired
	private RedisTemplate tradeRedisTemplate;

	@Override
	public void saveOrUpdate(Long code, MarketOrder entity) {
		tradeRedisTemplate.opsForHash().put(MARKET_ORDER_KEY, code, entity);
	}

	@Override
	public MarketOrder get(Long code) {
		return (MarketOrder)tradeRedisTemplate.opsForHash().get(MARKET_ORDER_KEY, code);
	}

}
