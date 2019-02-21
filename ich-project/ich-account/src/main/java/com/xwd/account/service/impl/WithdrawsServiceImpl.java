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
public class WithdrawsServiceImpl extends AbstractBaseService<Withdraws> implements WithdrawsService{

	@Autowired
	private WithdrawsDao withdrawsDao;
	
	public EntityDao<Withdraws,Long> getEntityDao() {
		return this.withdrawsDao;
	}
	
	@Override
	public int save(Withdraws entity) {
		return withdrawsDao.save(entity);
	}

	@Override
	public int update(Withdraws entity) {
		return withdrawsDao.update(entity);
	}

	@Override
	public int saveOrUpdate(Withdraws entity) {
		return withdrawsDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Withdraws entity) {
		withdrawsDao.delete(entity);
	}
	
}
