package com.xwd.product.service;

import com.frame.base.BaseService;
import com.xwd.product.model.ProductImage;


public interface ProductImageService extends BaseService<ProductImage>{

	public int save(ProductImage entity);

	public int update(ProductImage entity);

	public int saveOrUpdate(ProductImage entity);

	public void delete(ProductImage entity);
}
