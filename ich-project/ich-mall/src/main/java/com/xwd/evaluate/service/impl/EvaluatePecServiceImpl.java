package com.xwd.evaluate.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.evaluate.dao.EvaluatePecDao;
import com.xwd.evaluate.model.EvaluatePec;
import com.xwd.evaluate.service.EvaluatePecService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class EvaluatePecServiceImpl extends AbstractBaseService<EvaluatePec> implements EvaluatePecService{

	@Autowired
	private EvaluatePecDao evaluatePecDao;
	
	public EntityDao<EvaluatePec,Long> getEntityDao() {
		return this.evaluatePecDao;
	}
	
	@Override
	public int save(EvaluatePec entity) {
		return evaluatePecDao.save(entity);
	}

	@Override
	public int update(EvaluatePec entity) {
		return evaluatePecDao.update(entity);
	}

	@Override
	public int saveOrUpdate(EvaluatePec entity) {
		return evaluatePecDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(EvaluatePec entity) {
		evaluatePecDao.delete(entity);
	}

	@Override
	public int queryCommenTimes(Long customerNo) {
		return evaluatePecDao.queryCommenTimes(customerNo);
	}
	
}
