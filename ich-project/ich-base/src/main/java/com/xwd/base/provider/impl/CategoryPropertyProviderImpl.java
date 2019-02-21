package com.xwd.base.provider.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.xwd.base.constant.RedisBaseConstant;
import com.xwd.base.model.Category;
import com.xwd.base.model.CategoryProperty;
import com.xwd.base.provider.CategoryPropertyProvider;

@Service
public class CategoryPropertyProviderImpl implements CategoryPropertyProvider{
	
	@Autowired
	private RedisTemplate<String, Category> redisTemplate;

	@Override
	public void save(CategoryProperty entity) {
		if(entity != null){
			redisTemplate.opsForHash().put(RedisBaseConstant.BASE_CATEGORY_PROPERTY_KEY, entity.getId(), entity);	
		}
	}

	@Override
	public void update(CategoryProperty entity) {
		if(entity != null){
			redisTemplate.opsForHash().put(RedisBaseConstant.BASE_CATEGORY_PROPERTY_KEY, entity.getId(), entity);	
		}
	}

	@Override
	public void delete(Long id) {
		if(id != null){
			redisTemplate.opsForHash().delete(RedisBaseConstant.BASE_CATEGORY_PROPERTY_KEY, id);	
		}
	}

	@Override
	public CategoryProperty get(Long id) {
		return (CategoryProperty)redisTemplate.opsForHash().get(RedisBaseConstant.BASE_CATEGORY_PROPERTY_KEY, id);
	}

	@Override
	public List<CategoryProperty> findAll() {
		List<Object> list = redisTemplate.opsForHash().values(RedisBaseConstant.BASE_CATEGORY_PROPERTY_KEY);
		List<CategoryProperty> result = new ArrayList<CategoryProperty>();
		for(Object o : list){
			result.add((CategoryProperty)o);
		}
		return result;
	}

	@Override
	public List<CategoryProperty> find(Long categoryId) {
		List<Object> list = redisTemplate.opsForHash().values(RedisBaseConstant.BASE_CATEGORY_PROPERTY_KEY);
		List<CategoryProperty> result = new ArrayList<CategoryProperty>();
		CategoryProperty entity;
		for(Object o : list){
			entity = (CategoryProperty)o;
			if(entity.getCategoryId() == categoryId){
				result.add((CategoryProperty)o);
			}
		}
		return result;
	}

}
