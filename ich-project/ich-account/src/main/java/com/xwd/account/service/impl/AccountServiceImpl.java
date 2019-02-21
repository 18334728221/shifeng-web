package com.xwd.account.service.impl;

import java.util.HashMap;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.account.dao.AccountDao;
import com.xwd.account.model.Account;
import com.xwd.account.provider.AccountProvider;
import com.xwd.account.service.AccountService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class AccountServiceImpl extends AbstractBaseService<Account> implements AccountService{

	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountProvider accountProvider;
	
	public EntityDao<Account,Long> getEntityDao() {
		return this.accountDao;
	}
	
	@Override
	public int save(Account entity) {
		return accountDao.save(entity);
	}

	@Override
	public int update(Account entity) {
		return accountDao.update(entity);
	}

	@Override
	public int saveOrUpdate(Account entity) {
		return accountDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Account entity) {
		accountDao.delete(entity);
	}
	
	
	/**
	 * 更新提现金额
	 * T+n交易的时候 n > 0
	 * @return
	 */
	@Override
	public int updateWithdrawCash(){
		int n = accountDao.updateWithdrawCash();
		this.initCache();
		return n;
	}

	@Override
	public void initCache() {
		PageRequest<HashMap<String, Object>> pageRequest = new PageRequest<HashMap<String, Object>>();
		HashMap<String, Object> filters = new HashMap<String, Object>();
		pageRequest.setFilters(filters);
		int pageNumber = 1;
		pageRequest.setPageSize(100);
		Page<Account> page;
		while (true) {
			pageRequest.setPageNumber(pageNumber);
			page = this.findByPageRequest(pageRequest);
			for(Account entity : page.getResult()){
				this.accountProvider.update(entity, false);
			}
			if(pageNumber >= page.getLastPageNumber()){
				break;
			}
			pageNumber++;
		}
	}
}
