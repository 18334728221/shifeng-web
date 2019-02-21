package com.xwd.mall.service;

import com.frame.base.BaseService;
import com.xwd.mall.model.MallPickUp;


public interface MallPickUpService extends BaseService<MallPickUp>{

	public int save(MallPickUp entity);

	public int update(MallPickUp entity);

	public int saveOrUpdate(MallPickUp entity);

	public void delete(MallPickUp entity);
}
