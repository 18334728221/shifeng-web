package com.xwd.product.service;

import java.util.List;

import com.frame.base.BaseService;
import com.xwd.product.model.Product;


public interface ProductService extends BaseService<Product>{

	public int save(Product entity);

	public int update(Product entity);

	public int saveOrUpdate(Product entity);

	public void delete(Product entity);
	
	public Product findLastProduct();
	
	/**
	 * 查找除了userId以外的所有user列表
	 * @param pageRequest
	 * @return
	 */
	public List<Product> findExceptByProductId(Object ...params);
	
	/**
	 * 模糊查询  首页搜索
	 * @param pageRequest
	 * @return
	 */
	public List<Product> searchProduct(Object ...params);
	
	/**
	 * 首页条件查询
	 */
	public List<Product> findHomeIsNew (Object ...params);
	
	/**
	 * 查询每种属性product最后一条记录
	 */
	public Product findLastProductByCategory(Object... params);
	
}
