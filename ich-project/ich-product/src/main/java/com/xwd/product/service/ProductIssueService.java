package com.xwd.product.service;

import java.util.List;

import com.frame.base.BaseService;
import com.xwd.product.model.ProductIssue;
import com.xwd.product.model.ProductPurchase;


public interface ProductIssueService extends BaseService<ProductIssue>{

	public int save(ProductIssue entity);

	public int update(ProductIssue entity);

	public int saveOrUpdate(ProductIssue entity);

	public void delete(ProductIssue entity);
	
	/**
	 * 可入库数量
	 */
	public Long findProductAmount(Long productCode);
	
}
