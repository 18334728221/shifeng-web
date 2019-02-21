package com.xwd.msg.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;

import com.xwd.msg.model.*;
import com.xwd.msg.dao.*;
import com.xwd.msg.service.*;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class SmsUserServiceImpl extends AbstractBaseService<SmsUser> implements SmsUserService{

	@Autowired
	private SmsUserDao smsUserDao;
	
	public EntityDao<SmsUser,Long> getEntityDao() {
		return this.smsUserDao;
	}
	
	@Override
	public int save(SmsUser entity) {
		return smsUserDao.save(entity);
	}

	@Override
	public int update(SmsUser entity) {
		return smsUserDao.update(entity);
	}

	@Override
	public int saveOrUpdate(SmsUser entity) {
		return smsUserDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(SmsUser entity) {
		smsUserDao.delete(entity);
	}
	
}
