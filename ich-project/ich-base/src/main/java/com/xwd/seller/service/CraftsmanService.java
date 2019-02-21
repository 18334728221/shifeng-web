package com.xwd.seller.service;

import com.frame.base.BaseService;
import com.xwd.seller.model.Craftsman;


public interface CraftsmanService extends BaseService<Craftsman>{

	public int save(Craftsman entity);

	public int update(Craftsman entity);

	public int saveOrUpdate(Craftsman entity);

	public void delete(Craftsman entity);
	
	public Craftsman findLastCraftsman();
	
	/**
	 * 根据手机号或者email获得用户
	 * @param params
	 * @return
	 */
	public Craftsman getCustomerByEmailOrMobile(Object ...params);
}
