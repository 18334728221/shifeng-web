package com.xwd.log.service;

import com.frame.base.BaseService;
import com.xwd.log.model.LogSellerCommission;


public interface LogSellerCommissionService extends BaseService<LogSellerCommission>{

	public int save(LogSellerCommission entity);

	public int update(LogSellerCommission entity);

	public int saveOrUpdate(LogSellerCommission entity);

	public void delete(LogSellerCommission entity);
}
