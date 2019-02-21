package com.xwd.interact.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;

import com.xwd.interact.model.*;
import com.xwd.interact.dao.*;
import com.xwd.interact.service.*;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class AnswerServiceImpl extends AbstractBaseService<Answer> implements AnswerService{

	@Autowired
	private AnswerDao answerDao;
	
	public EntityDao<Answer,Long> getEntityDao() {
		return this.answerDao;
	}
	
	@Override
	public int save(Answer entity) {
		return answerDao.save(entity);
	}

	@Override
	public int update(Answer entity) {
		return answerDao.update(entity);
	}

	@Override
	public int saveOrUpdate(Answer entity) {
		return answerDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Answer entity) {
		answerDao.delete(entity);
	}
	
}
