package com.xwd.base.dao;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.base.model.Bank;


@Component
public class BankDao extends BaseMyIbatisDao<Bank, Long> {

	public Class<Bank> getEntityClass() {
		return Bank.class;
	}
	
	public int saveOrUpdate(Bank entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<Bank> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
