package com.xwd.trade.provider.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.xwd.engine.model.TradeRecord;
import com.xwd.trade.provider.TradeRecordProvider;

@Service
public class TradeRecordProviderImpl implements TradeRecordProvider{

	private final static String ENGINE_REDIS_LIST_KEY = "engine_redis_list";

	@Autowired
	private RedisTemplate<String, TradeRecord> tradeRedisTemplate;

	@Override
	public void push(TradeRecord entity) {
		tradeRedisTemplate.opsForList().leftPush(ENGINE_REDIS_LIST_KEY, entity);
	}

	@Override
	public TradeRecord pop() {
		return tradeRedisTemplate.opsForList().rightPop(ENGINE_REDIS_LIST_KEY);
	}
}
