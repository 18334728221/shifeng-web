package com.xwd.mall.provider.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.auth.Authenticator;
import com.auth.SecurityUtils;
import com.xwd.customer.model.Customer;
import com.xwd.mall.dto.ShoppingCart;
import com.xwd.mall.provider.ShoppingCartProvider;

@Service
public class ShoppingCartProviderImpl implements ShoppingCartProvider{
	
	private static final String SHOPPING_CART_KEY = "shopping_cart";
	
	@Autowired
	private Authenticator authenticator;
	@Autowired
	private RedisTemplate<String, ShoppingCart> redisTemplate;

	@Override
	public String save(ShoppingCart entity) {
		Customer user = (Customer) SecurityUtils.getUser();
		String id;
		if(user == null){
			id = authenticator.generateId();
		} else {
			id = user.getCustomerNo() + "";
		}
		entity.setId(SHOPPING_CART_KEY + user.getCustomerNo());
		redisTemplate.opsForValue().set(entity.getId(), entity);
		return id;
	}
	
	@Override
	public void update(ShoppingCart entity) {
		if(entity != null && StringUtils.isNotBlank(entity.getId())){
			redisTemplate.opsForValue().set(entity.getId(), entity);
		}
	}

	@Override
	public ShoppingCart get() {
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			return null;
		}
		return redisTemplate.opsForValue().get(SHOPPING_CART_KEY + user.getCustomerNo());
	}

	@Override
	public ShoppingCart get(String id) {
		return redisTemplate.opsForValue().get(SHOPPING_CART_KEY + id);
	}

	@Override
	public void delete() {
		Customer user = (Customer) SecurityUtils.getUser();
		if(user != null){
			redisTemplate.delete(SHOPPING_CART_KEY + user.getCustomerNo());
		}
	}

	@Override
	public void delete(String id) {
		redisTemplate.delete(SHOPPING_CART_KEY + id);
	}

}
