package com.xwd.account.dao;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.account.model.AccountBank;


@Component 
public class AccountBankDao extends BaseMyIbatisDao<AccountBank, Long> {

	public Class<AccountBank> getEntityClass() {
		return AccountBank.class;
	}
	
	public int saveOrUpdate(AccountBank entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<AccountBank> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
