package com.xwd.product.service;

import com.frame.base.BaseService;
import com.xwd.product.model.SkuImage;


public interface SkuImageService extends BaseService<SkuImage>{

	public int save(SkuImage entity);

	public int update(SkuImage entity);

	public int saveOrUpdate(SkuImage entity);

	public void delete(SkuImage entity);
}
