package com.xwd.account.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.account.dao.AccountBankDao;
import com.xwd.account.model.AccountBank;
import com.xwd.account.service.AccountBankService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class AccountBankServiceImpl extends AbstractBaseService<AccountBank> implements AccountBankService{

	@Autowired
	private AccountBankDao accountBankDao;
	
	public EntityDao<AccountBank,Long> getEntityDao() {
		return this.accountBankDao;
	}
	
	@Override
	public int save(AccountBank entity) {
		return accountBankDao.save(entity);
	}

	@Override
	public int update(AccountBank entity) {
		return accountBankDao.update(entity);
	}

	@Override
	public int saveOrUpdate(AccountBank entity) {
		return accountBankDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(AccountBank entity) {
		accountBankDao.delete(entity);
	}
	
}
