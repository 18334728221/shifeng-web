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
public class EvaluateImageServiceImpl extends AbstractBaseService<EvaluateImage> implements EvaluateImageService{

	@Autowired
	private EvaluateImageDao evaluateImageDao;
	
	public EntityDao<EvaluateImage,Long> getEntityDao() {
		return this.evaluateImageDao;
	}
	
	@Override
	public int save(EvaluateImage entity) {
		return evaluateImageDao.save(entity);
	}

	@Override
	public int update(EvaluateImage entity) {
		return evaluateImageDao.update(entity);
	}

	@Override
	public int saveOrUpdate(EvaluateImage entity) {
		return evaluateImageDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(EvaluateImage entity) {
		evaluateImageDao.delete(entity);
	}
	
}
