package com.xwd.base.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;

import com.xwd.base.model.*;
import com.xwd.base.dao.*;
import com.xwd.base.service.*;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class EvaluateContentServiceImpl extends AbstractBaseService<EvaluateContent> implements EvaluateContentService{

	@Autowired
	private EvaluateContentDao evaluateContentDao;
	
	public EntityDao<EvaluateContent,Long> getEntityDao() {
		return this.evaluateContentDao;
	}
	
	@Override
	public int save(EvaluateContent entity) {
		return evaluateContentDao.save(entity);
	}

	@Override
	public int update(EvaluateContent entity) {
		return evaluateContentDao.update(entity);
	}

	@Override
	public int saveOrUpdate(EvaluateContent entity) {
		return evaluateContentDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(EvaluateContent entity) {
		evaluateContentDao.delete(entity);
	}
	
}
