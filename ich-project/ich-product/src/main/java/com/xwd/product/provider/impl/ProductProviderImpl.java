package com.xwd.product.provider.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.xwd.product.model.Product;
import com.xwd.product.provider.ProductProvider;

@Service
public class ProductProviderImpl implements ProductProvider{

	private final static String REDIS_PRODUCT_KEY = "redis_product";
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Override
	public void save(Product entity) {
		if(entity == null){
			return;
		}
		redisTemplate.opsForHash().put(REDIS_PRODUCT_KEY, entity.getCode(), entity);
	}

	@Override
	public void update(Product entity) {
		if(entity == null){
			return;
		}
		redisTemplate.opsForHash().put(REDIS_PRODUCT_KEY, entity.getCode(), entity);
	}

	@Override
	public void delete(Long code) {
		redisTemplate.opsForHash().delete(REDIS_PRODUCT_KEY, code);
	}

	@Override
	public Product get(Long code) {
		return (Product)redisTemplate.opsForHash().get(REDIS_PRODUCT_KEY, code);
	}

}
