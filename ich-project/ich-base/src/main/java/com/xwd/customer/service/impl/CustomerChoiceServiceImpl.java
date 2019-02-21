package com.xwd.customer.service.impl;

import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.customer.dao.CustomerChoiceDao;
import com.xwd.customer.model.CustomerChoice;
import com.xwd.customer.service.CustomerChoiceService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class CustomerChoiceServiceImpl extends AbstractBaseService<CustomerChoice> implements CustomerChoiceService{

	@Autowired
	private CustomerChoiceDao customerChoiceDao;
	
	public EntityDao<CustomerChoice,Long> getEntityDao() {
		return this.customerChoiceDao;
	}
	
	@Override
	public int save(CustomerChoice entity) {
		return customerChoiceDao.save(entity);
	}

	@Override
	public int update(CustomerChoice entity) {
		return customerChoiceDao.update(entity);
	}

	@Override
	public int saveOrUpdate(CustomerChoice entity) {
		return customerChoiceDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(CustomerChoice entity) {
		customerChoiceDao.delete(entity);
	}

	@Override
	public List<CustomerChoice> findChoiceBy(Object... paras) {
		return customerChoiceDao.findChoiceBy(paras);
	}
	
}
