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
public class QuestionServiceImpl extends AbstractBaseService<Question> implements QuestionService{

	@Autowired
	private QuestionDao questionDao;
	
	public EntityDao<Question,Long> getEntityDao() {
		return this.questionDao;
	}
	
	@Override
	public int save(Question entity) {
		return questionDao.save(entity);
	}

	@Override
	public int update(Question entity) {
		return questionDao.update(entity);
	}

	@Override
	public int saveOrUpdate(Question entity) {
		return questionDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Question entity) {
		questionDao.delete(entity);
	}
	
}
