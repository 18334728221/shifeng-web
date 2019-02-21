package com.xwd.base.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.base.dao.MqDao;
import com.xwd.base.model.Mq;
import com.xwd.base.service.MqService;

/**
 * @author ljl
 */
@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class MqServiceImpl extends AbstractBaseService<Mq> implements MqService{

	@Autowired
	private MqDao mqDao;
	
	public EntityDao<Mq,Long> getEntityDao() {
		return this.mqDao;
	}
	
	@Override
	public int save(Mq entity) {
		return mqDao.save(entity);
	}

	@Override
	public int update(Mq entity) {
		return mqDao.update(entity);
	}

	@Override
	public int saveOrUpdate(Mq entity) {
		return mqDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Mq entity) {
		mqDao.delete(entity);
	}
	
}
