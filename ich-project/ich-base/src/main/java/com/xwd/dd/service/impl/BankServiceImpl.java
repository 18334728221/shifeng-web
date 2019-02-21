package com.xwd.dd.service.impl;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.base.dao.BankDao;
import com.xwd.base.model.Bank;
import com.xwd.dd.service.BankService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class BankServiceImpl extends AbstractBaseService<Bank> implements BankService{

	@Autowired
	private BankDao bankDao;
	
	public EntityDao<Bank,Long> getEntityDao() {
		return this.bankDao;
	}
	
	@Override
	public int save(Bank entity) {
		return bankDao.save(entity);
	}

	@Override
	public int update(Bank entity) {
		return bankDao.update(entity);
	}

	@Override
	public int saveOrUpdate(Bank entity) {
		return bankDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Bank entity) {
		bankDao.delete(entity);
	}
}
