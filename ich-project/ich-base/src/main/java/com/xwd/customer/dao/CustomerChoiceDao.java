package com.xwd.customer.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.customer.model.CustomerChoice;


@Component
public class CustomerChoiceDao extends BaseMyIbatisDao<CustomerChoice, Long> {

	public Class<CustomerChoice> getEntityClass() {
		return CustomerChoice.class;
	}
	
	public int saveOrUpdate(CustomerChoice entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<CustomerChoice> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	public List<CustomerChoice> findChoiceBy(Object... paras){
		return db().selectList(getEntityClass().getSimpleName() + ".findChoiceBy",map(paras));
	}
}
