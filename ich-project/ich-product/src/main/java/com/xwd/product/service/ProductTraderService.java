package com.xwd.product.service;

import com.frame.base.BaseService;
import com.xwd.product.model.ProductTrader;


public interface ProductTraderService extends BaseService<ProductTrader>{

	public int save(ProductTrader entity);

	public int update(ProductTrader entity);

	public int saveOrUpdate(ProductTrader entity);

	public void delete(ProductTrader entity);
}
