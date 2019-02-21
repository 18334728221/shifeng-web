package com.xwd.trade.provider.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.xwd.trade.model.TradeOrder;
import com.xwd.trade.provider.TradeOrderProvider;

@Service
public class TradeOrderProviderImpl implements TradeOrderProvider {

	// 买卖交易的顾客
	private final static String TRADE_ORDER_CUSTOMER_KEY = "trade_order_customer";
	private final static String TRADE_ORDER_UPDATE_LIST_KEY = "trade_order_update_list";
	
	@Autowired
	private RedisTemplate tradeRedisTemplate;
	
	@Override
	public void save(TradeOrder entity) {
		if (entity == null) {
			return;
		}
		tradeRedisTemplate.opsForHash().put(TRADE_ORDER_CUSTOMER_KEY + entity.getCustomerNo(), entity.getTxNo(), entity);
		tradeRedisTemplate.opsForSet().add(TRADE_ORDER_CUSTOMER_KEY, entity.getCustomerNo());
	}

	@Override
	public void delete(TradeOrder entity) {
		tradeRedisTemplate.opsForHash().delete(TRADE_ORDER_CUSTOMER_KEY + entity.getCustomerNo(), entity.getTxNo());
	}
	
	/**
	 * 跟新数据
	 */
	public void update(TradeOrder entity){
		tradeRedisTemplate.opsForHash().put(TRADE_ORDER_CUSTOMER_KEY + entity.getCustomerNo(), entity.getTxNo(), entity);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TradeOrder> getByCustomer(Long customerNo) {
		Set<Object> set = tradeRedisTemplate.opsForHash().keys(TRADE_ORDER_CUSTOMER_KEY + customerNo);
		List<TradeOrder> list = new ArrayList<TradeOrder>();
		if(set == null || set.isEmpty()){
			return list;
		}
		return tradeRedisTemplate.opsForHash().multiGet(TRADE_ORDER_CUSTOMER_KEY + customerNo, set);
	}

	@Override
	public TradeOrder get(Long customerNo, String txNo) {
		return (TradeOrder)tradeRedisTemplate.opsForHash().get(TRADE_ORDER_CUSTOMER_KEY + customerNo, txNo);
	}

	@Override
	public void push(TradeOrder entity) {
		tradeRedisTemplate.opsForList().leftPush(TRADE_ORDER_UPDATE_LIST_KEY, entity);
	}

	@Override
	public TradeOrder pop() {
		return (TradeOrder)tradeRedisTemplate.opsForList().rightPop(TRADE_ORDER_UPDATE_LIST_KEY);
	}

	
	
}
