package com.xwd.account.dao;

import org.springframework.stereotype.Component;

import com.xwd.account.model.Account;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class AccountDao extends BaseMyIbatisDao<Account, Long> {

	public Class<Account> getEntityClass() {
		return Account.class;
	}
	
	public int saveOrUpdate(Account entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<Account> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	/**
	 * 更新提现金额
	 * T+n交易的时候 n > 0
	 * @return
	 */
	public int updateWithdrawCash(){
		return db().update(this.getEntityClass().getSimpleName() + ".updateWithdrawCash");
	}
}
