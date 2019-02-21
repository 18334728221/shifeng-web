package com.xwd.product.dao;

import org.springframework.stereotype.Component;

import com.xwd.product.model.*;
import java.util.HashMap;
import java.util.List;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class ProductDao extends BaseMyIbatisDao<Product, Long> {

	public Class<Product> getEntityClass() {
		return Product.class;
	}
	
	public int saveOrUpdate(Product entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<Product> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	public Product findLastProduct(){
		return db().selectOne("Product.findLastProduct");
	}
	
	/**
	 * 查找除了productId以外的所有product列表
	 * @param pageRequest
	 * @return
	 */
	public List<Product> findExceptByProductId(Object ...params) {
		return db().selectList(getEntityClass().getSimpleName() + ".findExceptByProductId", map(params));
	}
	
	/**
	 * 首页搜索
	 */
	public List<Product> searchProduct(Object ...params) {
		return db().selectList(getEntityClass().getSimpleName() + ".searchProduct", map(params));
	}
	
	/**
	 * 首页条件查询
	 */
	public List<Product> findHomeIsNew (Object ...params) {
		return db().selectList(getEntityClass().getSimpleName() + ".findHomeIsNew", map(params));
	}
	
	/**
	 * 查询每种属性product最后一条记录
	 */
	public Product findLastProductByCategory(Object ...params){
		return db().selectOne(getEntityClass().getSimpleName() + ".findLastProductByCategory", map(params));
	}
}
