package com.xwd.securities.provider.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.xwd.base.util.SortUtils;
import com.xwd.securities.model.Stock;
import com.xwd.securities.provider.StockProvider;

/**
 * 股票操作实现类
 * @author ljl
 *
 */
@Service
public class StockProviderImpl implements StockProvider{
	
	private final static String REDIS_STOCK_KEY = "redis_stock";

	@Autowired
	private RedisTemplate<String, Stock> tradeRedisTemplate;
	
	@Override
	public void save(Stock stock) {
		if(stock == null){
			return;
		}
		tradeRedisTemplate.opsForHash().put(REDIS_STOCK_KEY, stock.getCode(), stock);
	}
	
	@Override
	public void update(Stock stock) {
		if(stock == null){
			return;
		}
		tradeRedisTemplate.opsForHash().put(REDIS_STOCK_KEY, stock.getCode(), stock);
	}

	@Override
	public List<Stock> find() {
		Set<Object> keys = tradeRedisTemplate.opsForHash().keys(REDIS_STOCK_KEY);
		List<Stock> list = new ArrayList<Stock>();
		List<Object> objs = (List<Object>)tradeRedisTemplate.opsForHash().multiGet(REDIS_STOCK_KEY, keys);
		for(Object o : objs){
			list.add((Stock)o);
		}
		return list;
	}

	@Override
	public void clear() {
		tradeRedisTemplate.delete(REDIS_STOCK_KEY);
	}

	@Override
	public Stock get(Long code) {
		return (Stock)tradeRedisTemplate.opsForHash().get(REDIS_STOCK_KEY, code);
	}

	@Override
	public List<Stock> find(int pageNo, int pageSize) {
		List<Stock> list = this.find();
		if(pageNo < 0){
			pageNo = 0;
		} 
		if(pageSize <= 0){
			pageSize = 10;
		}
		int start = pageNo * pageSize;
		int end = start + pageSize;
		if(list!=null && list.size()>end){
			return list.subList(start, end);
		}else{
			return list;
		}
	}

	@Override
	public List<Stock> findBySort(int pageNo, int pageSize, String fieldName, Boolean isAsc) {
		List<Stock> list = this.find(pageNo, pageSize);
		SortUtils.sort(list, isAsc, fieldName);
		return list;
	}

	@Override
	public List<Stock> find(Long categoryId, int pageNo, int pageSize) {
		List<Stock> list = this.findByCateory(categoryId);
		if(pageNo < 0){
			pageNo = 0;
		} 
		if(pageSize <= 0){
			pageSize = 10;
		}
		int start = pageNo * pageSize;
		int end = start + pageSize;
		if(list!=null && list.size()>end){
			return list.subList(start, end);
		}else{
			return list;
		}
	}

	@Override
	public List<Stock> findBySort(Long categoryId, int pageNo, int pageSize, String fieldName, Boolean isAsc) {
		List<Stock> list = this.find(categoryId, pageNo, pageSize);
		SortUtils.sort(list, isAsc, fieldName);
		return list;
	}
	
	private List<Stock> findByCateory(Long categoryId) {
		Set<Object> keys = tradeRedisTemplate.opsForHash().keys(REDIS_STOCK_KEY);
		List<Stock> list = new ArrayList<Stock>();
		List<Object> objs = (List<Object>)tradeRedisTemplate.opsForHash().multiGet(REDIS_STOCK_KEY, keys);
		Stock stock;
		for(Object o : objs){
			stock = (Stock)o;
			if(stock.getCategoryId() == categoryId){
				list.add(stock);
			}
		}
		return list;
	}

	@Override
	public boolean isOverLimitUpPrice(Long code, BigDecimal price) {
		Stock stock = this.get(code);
		if(stock == null){
			return false;
		}
		if(stock.getRose() > 0){
			if(stock.getLimitUpPrice().compareTo(price) < 0){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isOverLimitDownPrice(Long code, BigDecimal price) {
		Stock stock = this.get(code);
		if(stock == null){
			return false;
		}
		if(stock.getRose() > 0){
			if(stock.getLimitDownPrice().compareTo(price) > 0){
				return true;
			}
		}
		return false;
	}

}
