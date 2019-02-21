package com.xwd.product.service.impl;

import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.product.dao.ProductPurchaseDao;
import com.xwd.product.model.ProductPurchase;
import com.xwd.product.service.ProductPurchaseService;

@Component
@Transactional
@Aspect
public class ProductPurchaseServiceImpl extends AbstractBaseService<ProductPurchase> implements ProductPurchaseService{

	@Autowired
	private ProductPurchaseDao productPurchaseDao;
	
	public EntityDao<ProductPurchase,Long> getEntityDao() {
		return this.productPurchaseDao;
	}
	
	@Override
	public int save(ProductPurchase entity) {
		return productPurchaseDao.save(entity);
	}

	@Override
	public int update(ProductPurchase entity) {
		return productPurchaseDao.update(entity);
	}
	
	@Override
	public int saveOrUpdate(ProductPurchase entity) {
		return productPurchaseDao.saveOrUpdate(entity);
	}
	
	@Override
	public void delete(ProductPurchase entity) {
		productPurchaseDao.delete(entity);
	}

	@Override
	public int updateWithBidAll(Long productCode) {
		return productPurchaseDao.updateWithBidAll(productCode);
	}
	
	public Long getPurchaseNumByCode(Object... paras) {
		return productPurchaseDao.getPurchaseNumByCode(paras);
	}

	@Override
	public Long getPurchaseNumByCustomer(Object... paras) {
		return productPurchaseDao.getPurchaseNumByCustomer(paras);
	}

	@Override
	public List<ProductPurchase> findByCustomer(Object... paras) {
		return productPurchaseDao.findByCustomer(paras);
	}
	
}
