package com.xwd.product.service;

import com.frame.base.BaseService;
import com.xwd.product.model.Sku;


public interface SkuService extends BaseService<Sku>{

	public int save(Sku entity);

	public int update(Sku entity);

	public int saveOrUpdate(Sku entity);

	public void delete(Sku entity);
	
	/**
	 * 可入库数量
	 */
	public Long findProductAmount(Long productCode);
	
	/**
	 * 查询最后一条sku
	 * productCode
	 */
	public Sku findLastSkuByProduct(Object... params);
	
}
