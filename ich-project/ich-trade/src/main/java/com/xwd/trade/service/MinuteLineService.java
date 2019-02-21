package com.xwd.trade.service;

import com.frame.base.BaseService;
import com.xwd.trade.model.MinuteLine;


public interface MinuteLineService extends BaseService<MinuteLine>{

	public int save(MinuteLine entity);

	public int update(MinuteLine entity);

	public int saveOrUpdate(MinuteLine entity);

	public void delete(MinuteLine entity);
}
