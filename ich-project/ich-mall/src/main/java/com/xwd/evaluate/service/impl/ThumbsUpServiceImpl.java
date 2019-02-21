package com.xwd.evaluate.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.evaluate.dao.*;
import com.xwd.evaluate.model.*;
import com.xwd.evaluate.service.*;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class ThumbsUpServiceImpl extends AbstractBaseService<ThumbsUp> implements ThumbsUpService{

	@Autowired
	private ThumbsUpDao thumbsUpDao;
	
	public EntityDao<ThumbsUp,Long> getEntityDao() {
		return this.thumbsUpDao;
	}
	
	@Override
	public int save(ThumbsUp entity) {
		return thumbsUpDao.save(entity);
	}

	@Override
	public int update(ThumbsUp entity) {
		return thumbsUpDao.update(entity);
	}

	@Override
	public int saveOrUpdate(ThumbsUp entity) {
		return thumbsUpDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(ThumbsUp entity) {
		thumbsUpDao.delete(entity);
	}
	
	@Override
	public Long queryUserNum(Long userId) {
		return thumbsUpDao.queryUserNum(userId);
	}
	
}
