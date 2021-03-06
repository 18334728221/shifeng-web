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
public class ProductTraderServiceImpl extends AbstractBaseService<ProductTrader> implements ProductTraderService{

	@Autowired
	private ProductTraderDao productTraderDao;
	
	public EntityDao<ProductTrader,Long> getEntityDao() {
		return this.productTraderDao;
	}
	
	@Override
	public int save(ProductTrader entity) {
		return productTraderDao.save(entity);
	}

	@Override
	public int update(ProductTrader entity) {
		return productTraderDao.update(entity);
	}

	@Override
	public int saveOrUpdate(ProductTrader entity) {
		return productTraderDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(ProductTrader entity) {
		productTraderDao.delete(entity);
	}
	
}
