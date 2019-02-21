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
public class EmailCustomerServiceImpl extends AbstractBaseService<EmailCustomer> implements EmailCustomerService{

	@Autowired
	private EmailCustomerDao emailCustomerDao;
	
	public EntityDao<EmailCustomer,Long> getEntityDao() {
		return this.emailCustomerDao;
	}
	
	@Override
	public int save(EmailCustomer entity) {
		return emailCustomerDao.save(entity);
	}

	@Override
	public int update(EmailCustomer entity) {
		return emailCustomerDao.update(entity);
	}

	@Override
	public int saveOrUpdate(EmailCustomer entity) {
		return emailCustomerDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(EmailCustomer entity) {
		emailCustomerDao.delete(entity);
	}
	
}
