package com.xwd.log.service;

import com.frame.base.BaseService;
import com.xwd.log.model.LogBuyerCommission;


public interface LogBuyerCommissionService extends BaseService<LogBuyerCommission>{

	public int save(LogBuyerCommission entity);

	public int update(LogBuyerCommission entity);

	public int saveOrUpdate(LogBuyerCommission entity);

	public void delete(LogBuyerCommission entity);
}
