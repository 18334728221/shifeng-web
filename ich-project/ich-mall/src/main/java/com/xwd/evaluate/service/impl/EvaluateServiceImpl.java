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
public class EvaluateServiceImpl extends AbstractBaseService<Evaluate> implements EvaluateService{

	@Autowired
	private EvaluateDao evaluateDao;
	
	public EntityDao<Evaluate,Long> getEntityDao() {
		return this.evaluateDao;
	}
	
	@Override
	public int save(Evaluate entity) {
		return evaluateDao.save(entity);
	}

	@Override
	public int update(Evaluate entity) {
		return evaluateDao.update(entity);
	}

	@Override
	public int saveOrUpdate(Evaluate entity) {
		return evaluateDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Evaluate entity) {
		evaluateDao.delete(entity);
	}
	
}
