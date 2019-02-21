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
public class ProductVideoServiceImpl extends AbstractBaseService<ProductVideo> implements ProductVideoService{

	@Autowired
	private ProductVideoDao productVideoDao;
	
	public EntityDao<ProductVideo,Long> getEntityDao() {
		return this.productVideoDao;
	}
	
	@Override
	public int save(ProductVideo entity) {
		return productVideoDao.save(entity);
	}

	@Override
	public int update(ProductVideo entity) {
		return productVideoDao.update(entity);
	}

	@Override
	public int saveOrUpdate(ProductVideo entity) {
		return productVideoDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(ProductVideo entity) {
		productVideoDao.delete(entity);
	}
	
}
