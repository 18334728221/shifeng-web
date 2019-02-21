package com.xwd.product.service;

import java.util.List;

import com.frame.base.BaseService;
import com.xwd.product.model.ProductPurchase;


public interface ProductPurchaseService extends BaseService<ProductPurchase>{

	public int save(ProductPurchase entity);

	public int update(ProductPurchase entity);
	
	public int saveOrUpdate(ProductPurchase entity);

	public void delete(ProductPurchase entity);
	
	/**
	 * 全部中签
	 * @param productCode
	 * @return
	 */
	public int updateWithBidAll(Long productCode);
	
	/**
	 * 根据产品获得对应的申购总数
	 * @param paras
	 * @return
	 */
	public Long getPurchaseNumByCode(Object... paras);
	
	/**
	 * 根据顾客获得申购总数
	 * @param paras
	 * @return
	 */
	public Long getPurchaseNumByCustomer(Object... paras);
	
	/**
	 * 根据股票编号、顾客No's查找
	 * @param paras
	 * @return
	 */
	public List<ProductPurchase> findByCustomer(Object... paras);
}
