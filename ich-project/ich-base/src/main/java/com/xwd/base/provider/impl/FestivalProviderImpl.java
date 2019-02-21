package com.xwd.base.provider.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.frame.util.CollectionUtils;
import com.xwd.base.constant.RedisBaseConstant;
import com.xwd.base.model.Festival;
import com.xwd.base.model.ImageServer;
import com.xwd.base.provider.FestivalProvider;

@Service
public class FestivalProviderImpl implements FestivalProvider{

	@Autowired
	private RedisTemplate<String, ImageServer> redisTemplate;
	@Override
	public void saveOrUpdate(Festival entity) {
		if(entity != null){
			redisTemplate.opsForHash().put(RedisBaseConstant.BASE_FESTIVAL_KEY, entity.getId(), entity);	
		}
	}

	@Override
	public void delete(String id) {
		redisTemplate.opsForHash().delete(RedisBaseConstant.BASE_FESTIVAL_KEY, id);
	}

	@Override
	public Festival get(String id) {
		 return (Festival)redisTemplate.opsForHash().get(RedisBaseConstant.BASE_FESTIVAL_KEY, id);
	}

	@Override
	public List<Festival> findAll() {
		Set<Object> set = redisTemplate.opsForHash().keys(RedisBaseConstant.BASE_FESTIVAL_KEY);
		List<Object> list = redisTemplate.opsForHash().multiGet(RedisBaseConstant.BASE_FESTIVAL_KEY, set);
		List<Festival> result = new ArrayList<Festival>();
		for(Object o : list){
			result.add((Festival)o);
		}
		
		return result;
	}

	@Override
	public void delete(Long id) {
		redisTemplate.opsForHash().delete(RedisBaseConstant.BASE_FESTIVAL_KEY, id);
	}

	@Override
	public void deleteByIds(String ids) {
		List<Long> list = CollectionUtils.splitAsLong(ids);
		if(list.isEmpty()){
			return;
		}
		List<Festival> result = this.findAll();
		for(Festival obj : result){
			if(list.contains(obj.getId())){
				redisTemplate.opsForHash().delete(RedisBaseConstant.BASE_FESTIVAL_KEY, obj.getId());
			}
		}
	}

	@Override
	public List<Festival> findIsFestival() {
		List<Festival> result = new ArrayList<Festival>();
		List<Festival> list = this.findAll();
		Calendar c = Calendar.getInstance();
		for(Festival obj : list){
			if(obj.getStartTime().before(c) && obj.getEndTime().after(c)){
				result.add(obj);
			}
		}
		return result;
	}

}
