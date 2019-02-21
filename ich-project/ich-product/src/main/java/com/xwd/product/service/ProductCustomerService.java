package com.xwd.product.service;

import com.frame.base.BaseService;
import com.xwd.product.model.ProductCustomer;


public interface ProductCustomerService extends BaseService<ProductCustomer>{

	public int save(ProductCustomer entity);

	public int update(ProductCustomer entity);

	public int saveOrUpdate(ProductCustomer entity);

	public void delete(ProductCustomer entity);
}
