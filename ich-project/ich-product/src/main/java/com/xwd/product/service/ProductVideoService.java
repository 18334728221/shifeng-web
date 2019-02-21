package com.xwd.product.service;

import com.frame.base.BaseService;
import com.xwd.product.model.ProductVideo;


public interface ProductVideoService extends BaseService<ProductVideo>{

	public int save(ProductVideo entity);

	public int update(ProductVideo entity);

	public int saveOrUpdate(ProductVideo entity);

	public void delete(ProductVideo entity);
}
