package com.xwd.product.service.impl;

import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.product.dao.ProductDao;
import com.xwd.product.model.Product;
import com.xwd.product.service.ProductService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class ProductServiceImpl extends AbstractBaseService<Product> implements ProductService{

	@Autowired
	private ProductDao productDao;
	
	
	public EntityDao<Product,Long> getEntityDao() {
		return this.productDao;
	}
	
	@Override
	public int save(Product entity) {
		return productDao.save(entity);
	}

	@Override
	public int update(Product entity) {
		return productDao.update(entity);
	}

	@Override
	public int saveOrUpdate(Product entity) {
		
		return productDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Product entity) {
		productDao.delete(entity);
	}

	/**
	 * 查询最后product一条记录
	 */
	public Product findLastProduct() {
		return productDao.findLastProduct();
	}
	
	/**
	 * 查询每种属性product最后一条记录
	 */
	public Product findLastProductByCategory() {
		return productDao.findLastProduct();
	}
	

	/**
	 * 查找除了ProductId以外的所有Product列表
	 * @param pageRequest
	 * @return
	 */
	public List<Product> findExceptByProductId(Object ...params) {
		return this.productDao.findExceptByProductId(params);
	}

	/**
	 * 首页搜索
	 */
	public List<Product> searchProduct(Object ...params) {
		return  this.productDao.searchProduct(params);
	}
	
	/**
	 * 首页条件查询
	 */
	public List<Product> findHomeIsNew(Object... params) {
		return  this.productDao.findHomeIsNew(params);
	}

	@Override
	public Product findLastProductByCategory(Object... params) {
		return this.productDao.findLastProductByCategory(params);
	}
	
	
}
