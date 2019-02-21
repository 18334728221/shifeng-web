package com.xwd.seller.service;

import java.util.List;

import com.frame.base.BaseService;
import com.xwd.seller.model.CraftsmanCategory;


public interface CraftsmanCategoryService extends BaseService<CraftsmanCategory>{

	public int save(CraftsmanCategory entity);

	public int update(CraftsmanCategory entity);

	public int saveOrUpdate(CraftsmanCategory entity);

	public void delete(CraftsmanCategory entity);
	
	/** 
	 * 根据craftsmanNo查询分配的产品种类
	 * @param userId
	 **/
	public List<CraftsmanCategory> findByCraftsmanNo(Long craftsmanNo);
	
}
