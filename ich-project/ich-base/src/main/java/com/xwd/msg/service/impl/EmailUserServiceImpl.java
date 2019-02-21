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
public class EmailUserServiceImpl extends AbstractBaseService<EmailUser> implements EmailUserService{

	@Autowired
	private EmailUserDao emailUserDao;
	
	public EntityDao<EmailUser,Long> getEntityDao() {
		return this.emailUserDao;
	}
	
	@Override
	public int save(EmailUser entity) {
		return emailUserDao.save(entity);
	}

	@Override
	public int update(EmailUser entity) {
		return emailUserDao.update(entity);
	}

	@Override
	public int saveOrUpdate(EmailUser entity) {
		return emailUserDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(EmailUser entity) {
		emailUserDao.delete(entity);
	}
	
}
