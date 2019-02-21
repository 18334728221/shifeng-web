package com.xwd.base.provider.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.xwd.base.constant.RedisBaseConstant;
import com.xwd.base.model.ImageServer;
import com.xwd.base.provider.ImageServerProvider;

@Service
public class ImageServerProviderImpl implements ImageServerProvider{

	@Autowired
	private RedisTemplate<String, ImageServer> redisTemplate;
	
	@Override
	public void saveOrUpdate(ImageServer entity) {
		if(entity != null){
			redisTemplate.opsForHash().put(RedisBaseConstant.BASE_IMAGE_SERVER_KEY, entity.getId(), entity);	
		}
	}

	@Override
	public void delete(Long id) {
		redisTemplate.opsForHash().delete(RedisBaseConstant.BASE_IMAGE_SERVER_KEY, id);
	}

	@Override
	public ImageServer get(Long id) {
		 return (ImageServer)redisTemplate.opsForHash().get(RedisBaseConstant.BASE_IMAGE_SERVER_KEY, id);
	}

	@Override
	public ImageServer getByType(Byte serverType) {
		List<ImageServer> list = this.findAll();
		for(ImageServer entity : list){
			if(entity.getServerType() == serverType){
				return entity;
			}
		}
		return null;
	}

	@Override
	public List<ImageServer> findAll() {
		Set<Object> set = redisTemplate.opsForHash().keys(RedisBaseConstant.BASE_IMAGE_SERVER_KEY);
		List<Object> list = redisTemplate.opsForHash().multiGet(RedisBaseConstant.BASE_IMAGE_SERVER_KEY, set);
		List<ImageServer> result = new ArrayList<ImageServer>();
		for(Object o : list){
			result.add((ImageServer)o);
		}
		return result;
	}
}
