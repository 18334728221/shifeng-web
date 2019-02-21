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
public class SkuImageServiceImpl extends AbstractBaseService<SkuImage> implements SkuImageService{

	@Autowired
	private SkuImageDao skuImageDao;
	
	public EntityDao<SkuImage,Long> getEntityDao() {
		return this.skuImageDao;
	}
	
	@Override
	public int save(SkuImage entity) {
		return skuImageDao.save(entity);
	}

	@Override
	public int update(SkuImage entity) {
		return skuImageDao.update(entity);
	}

	@Override
	public int saveOrUpdate(SkuImage entity) {
		return skuImageDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(SkuImage entity) {
		skuImageDao.delete(entity);
	}
	
}
