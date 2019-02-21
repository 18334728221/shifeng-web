package com.xwd.account.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.account.dao.*;
import com.xwd.account.model.*;
import com.xwd.account.service.*;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class DepositsServiceImpl extends AbstractBaseService<Deposits> implements DepositsService{

	@Autowired
	private DepositsDao depositsDao;
	
	public EntityDao<Deposits,Long> getEntityDao() {
		return this.depositsDao;
	}
	
	@Override
	public int save(Deposits entity) {
		return depositsDao.save(entity);
	}

	@Override
	public int update(Deposits entity) {
		return depositsDao.update(entity);
	}

	@Override
	public int saveOrUpdate(Deposits entity) {
		return depositsDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Deposits entity) {
		depositsDao.delete(entity);
	}
	
}
