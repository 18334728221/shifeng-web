package com.xwd.trade.provider.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.xwd.redis.constant.RedisTradeConstant;
import com.xwd.trade.model.Trade;
import com.xwd.trade.provider.TradeProvider;

@Service
public class TradeProviderImpl implements TradeProvider {

	@Autowired
	private RedisTemplate<String, Trade> tradeRedisTemplate;

	@Override
	public void put(Trade entity) {
		if (entity == null) {
			return;
		}
		tradeRedisTemplate.opsForList().leftPush(RedisTradeConstant.TRADE_LIST_KEY, entity);
	}

	@Override
	public Trade pop() {
		return (Trade) tradeRedisTemplate.opsForList().rightPop(RedisTradeConstant.TRADE_LIST_KEY);
	}

}
