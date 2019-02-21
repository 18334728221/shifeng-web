package com.xwd.base.provider.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.xwd.base.constant.RedisBaseConstant;
import com.xwd.base.model.Category;
import com.xwd.base.provider.CategoryProvider;

@Service
public class CategoryProviderImpl implements CategoryProvider{
	
	@Autowired
	private RedisTemplate<String, Category> redisTemplate;

	@Override
	public void save(Category entity) {
		if(entity != null){
			redisTemplate.opsForHash().put(RedisBaseConstant.BASE_CATEGORY_KEY, entity.getId(), entity);	
		}
	}

	@Override
	public void update(Category entity) {
		if(entity != null){
			redisTemplate.opsForHash().put(RedisBaseConstant.BASE_CATEGORY_KEY, entity.getId(), entity);	
		}
	}

	@Override
	public void delete(Long id) {
		if(id != null){
			redisTemplate.opsForHash().delete(RedisBaseConstant.BASE_CATEGORY_KEY, id);	
		}
	}

	@Override
	public Category get(Long id) {
		return (Category)redisTemplate.opsForHash().get(RedisBaseConstant.BASE_CATEGORY_KEY, id);
	}

	@Override
	public List<Category> findAll() {
		List<Object> list = redisTemplate.opsForHash().values(RedisBaseConstant.BASE_CATEGORY_KEY);
		List<Category> result = new ArrayList<Category>();
		for(Object o : list){
			result.add((Category)o);
		}
		return result;
	}

}
