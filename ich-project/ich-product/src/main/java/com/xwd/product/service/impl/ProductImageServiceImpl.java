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
public class ProductImageServiceImpl extends AbstractBaseService<ProductImage> implements ProductImageService{

	@Autowired
	private ProductImageDao productImageDao;
	
	public EntityDao<ProductImage,Long> getEntityDao() {
		return this.productImageDao;
	}
	
	@Override
	public int save(ProductImage entity) {
		return productImageDao.save(entity);
	}

	@Override
	public int update(ProductImage entity) {
		return productImageDao.update(entity);
	}

	@Override
	public int saveOrUpdate(ProductImage entity) {
		return productImageDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(ProductImage entity) {
		productImageDao.delete(entity);
	}
	
}
