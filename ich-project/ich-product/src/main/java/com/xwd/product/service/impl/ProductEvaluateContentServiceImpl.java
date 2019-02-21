package com.xwd.product.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.product.dao.ProductEvaluateContentDao;
import com.xwd.product.model.ProductEvaluateContent;
import com.xwd.product.service.ProductEvaluateContentService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class ProductEvaluateContentServiceImpl extends AbstractBaseService<ProductEvaluateContent> implements ProductEvaluateContentService{

	@Autowired
	private ProductEvaluateContentDao productEvaluateContentDao;
	
	public EntityDao<ProductEvaluateContent,Long> getEntityDao() {
		return this.productEvaluateContentDao;
	}
	
	@Override
	public int save(ProductEvaluateContent entity) {
		return productEvaluateContentDao.save(entity);
	}

	@Override
	public int update(ProductEvaluateContent entity) {
		return productEvaluateContentDao.update(entity);
	}

	@Override
	public int saveOrUpdate(ProductEvaluateContent entity) {
		return productEvaluateContentDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(ProductEvaluateContent entity) {
		productEvaluateContentDao.delete(entity);
	}
	
	@Override
	public int queryTimes(Long productCode) {
		return productEvaluateContentDao.queryTimes(productCode);
	}
	
}
