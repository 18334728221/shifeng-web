package com.xwd.base.provider.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.frame.util.CalendarUtils;
import com.frame.util.CollectionUtils;
import com.xwd.base.constant.RedisBaseConstant;
import com.xwd.base.constant.SysConfigConstant;
import com.xwd.base.model.SysConfig;
import com.xwd.base.provider.SysConfigProvider;

@Service
public class SysConfigProviderImpl implements SysConfigProvider{
	
	@Autowired
	private RedisTemplate<String, SysConfig> redisTemplate;

	@Override
	public void saveOrUpdate(SysConfig entity) {
		if(entity != null){
			redisTemplate.opsForHash().put(RedisBaseConstant.BASE_SYS_CONFIG_KEY, entity.getConfigKey(), entity);	
		}
	}

	@Override
	public void delete(String key) {
		if(StringUtils.isNotBlank(key)){
			redisTemplate.opsForHash().delete(RedisBaseConstant.BASE_SYS_CONFIG_KEY, key);	
		}
	}

	@Override
	public SysConfig get(String key) {
		return (SysConfig)redisTemplate.opsForHash().get(RedisBaseConstant.BASE_SYS_CONFIG_KEY, key);
	}
	
	@Override
	public String getValue(String key){
		SysConfig entity = (SysConfig)redisTemplate.opsForHash().get(RedisBaseConstant.BASE_SYS_CONFIG_KEY, key);
		if(entity == null){
			return null;
		}
		return entity.getConfigValue();
	}
	

	@Override
	public List<SysConfig> findAll() {
		List<Object> list = redisTemplate.opsForHash().values(RedisBaseConstant.BASE_SYS_CONFIG_KEY);
		List<SysConfig> result = new ArrayList<SysConfig>();
		for(Object o : list){
			result.add((SysConfig)o);
		}
		return result;
	}

	@Override
	public void delete(Long id) {
		List<SysConfig> list = this.findAll();
		for(SysConfig obj : list){
			if(obj.getId() == id){
				redisTemplate.opsForHash().delete(RedisBaseConstant.BASE_SYS_CONFIG_KEY, obj.getConfigKey());
			}
		}
	}

	@Override
	public void deleteByIds(String ids) {
		List<Long> list = CollectionUtils.splitAsLong(ids);
		if(list.isEmpty()){
			return;
		}
		List<SysConfig> result = this.findAll();
		for(SysConfig obj : result){
			if(list.contains(obj.getId())){
				redisTemplate.opsForHash().delete(RedisBaseConstant.BASE_SYS_CONFIG_KEY, obj.getConfigKey());
			}
		}
	}

	@Override
	public Boolean isTradeTime() {
		Boolean flag = true;
		
		Calendar c = Calendar.getInstance();
		String beginInMorning = getValue(SysConfigConstant.OPEING_TIME_IN_MORNING);
		// 上午开盘时间
		Long value1 = Long.valueOf(beginInMorning.replace(":",""));
		// 上午收盘时间
		String closeInMoring = getValue(SysConfigConstant.CLOSING_TIME_IN_MORNING);
		Long value2 = Long.valueOf(closeInMoring.replace(":",""));
		
		// 下午开盘时间
		String beginInAfternoon = getValue(SysConfigConstant.OPEING_TIME_IN_AFTERNOON);
		Long value3 = Long.valueOf(beginInAfternoon.replace(":",""));
		// 下午收盘时间
		String closeInAfternoon = getValue(SysConfigConstant.CLOSING_TIME_IN_AFTERNOON);
		closeInAfternoon = "23:00:00";
		Long value4 = Long.valueOf(closeInAfternoon.replace(":",""));
		Long nowTime = Long.valueOf(CalendarUtils.convertStrPattern(c, "HHmmss"));
		if(value1 > nowTime || (nowTime > value2 && nowTime < value3) || nowTime > value4){
			flag = false;
		}
		return flag;
	}


}
