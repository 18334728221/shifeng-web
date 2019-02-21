package com.xwd.product.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;

import com.xwd.product.model.*;
import com.xwd.product.dao.*;
import com.xwd.product.service.*;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class ProductCustomerServiceImpl extends AbstractBaseService<ProductCustomer> implements ProductCustomerService{

	@Autowired
	private ProductCustomerDao productCustomerDao;
	
	public EntityDao<ProductCustomer,Long> getEntityDao() {
		return this.productCustomerDao;
	}
	
	@Override
	public int save(ProductCustomer entity) {
		return productCustomerDao.save(entity);
	}

	@Override
	public int update(ProductCustomer entity) {
		return productCustomerDao.update(entity);
	}

	@Override
	public int saveOrUpdate(ProductCustomer entity) {
		return productCustomerDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(ProductCustomer entity) {
		productCustomerDao.delete(entity);
	}
	
}
