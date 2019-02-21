package com.xwd.product.service;

import com.frame.base.BaseService;
import com.xwd.product.model.ProductEvaluateContent;


public interface ProductEvaluateContentService extends BaseService<ProductEvaluateContent>{

	public int save(ProductEvaluateContent entity);

	public int update(ProductEvaluateContent entity);

	public int saveOrUpdate(ProductEvaluateContent entity);

	public void delete(ProductEvaluateContent entity);
	
	//查询评论次数
	public int queryTimes(Long productCode);
	
}
