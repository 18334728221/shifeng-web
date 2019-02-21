package com.xwd.evaluate.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.evaluate.dao.EvaluateBecDao;
import com.xwd.evaluate.model.EvaluateBec;
import com.xwd.evaluate.service.EvaluateBecService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class EvaluateBecServiceImpl extends AbstractBaseService<EvaluateBec> implements EvaluateBecService{

	@Autowired
	private EvaluateBecDao evaluateBecDao;
	
	public EntityDao<EvaluateBec,Long> getEntityDao() {
		return this.evaluateBecDao;
	}
	
	@Override
	public int save(EvaluateBec entity) {
		return evaluateBecDao.save(entity);
	}

	@Override
	public int update(EvaluateBec entity) {
		return evaluateBecDao.update(entity);
	}

	@Override
	public int saveOrUpdate(EvaluateBec entity) {
		return evaluateBecDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(EvaluateBec entity) {
		evaluateBecDao.delete(entity);
	}
	
	@Override
	public long queryCommenTimes(long customerNo) {
		return evaluateBecDao.queryCommenTimes(customerNo);
	}
	
	
}
