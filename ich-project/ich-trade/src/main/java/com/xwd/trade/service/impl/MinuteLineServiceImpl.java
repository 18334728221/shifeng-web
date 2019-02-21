package com.xwd.trade.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.trade.dao.MinuteLineDao;
import com.xwd.trade.model.MinuteLine;
import com.xwd.trade.service.MinuteLineService;

@Component
@Transactional
@Aspect
public class MinuteLineServiceImpl extends AbstractBaseService<MinuteLine> implements MinuteLineService{

	@Autowired
	private MinuteLineDao minuteLineDao;
	
	public EntityDao<MinuteLine,Long> getEntityDao() {
		return this.minuteLineDao;
	}
	
	@Override
	public int save(MinuteLine entity) {
		return minuteLineDao.save(entity);
	}

	@Override
	public int update(MinuteLine entity) {
		return minuteLineDao.update(entity);
	}

	@Override
	public int saveOrUpdate(MinuteLine entity) {
		return minuteLineDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(MinuteLine entity) {
		minuteLineDao.delete(entity);
	}
	
}
