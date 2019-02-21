package com.xwd.msg.service;

import com.frame.base.BaseService;
import com.xwd.msg.model.CraftsmanLogistics;


public interface CraftsmanLogisticsService extends BaseService<CraftsmanLogistics>{

	public int save(CraftsmanLogistics entity);

	public int update(CraftsmanLogistics entity);

	public int saveOrUpdate(CraftsmanLogistics entity);

	public void delete(CraftsmanLogistics entity);
}
