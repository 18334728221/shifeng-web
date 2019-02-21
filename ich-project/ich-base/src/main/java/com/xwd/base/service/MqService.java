package com.xwd.base.service;

import com.frame.base.BaseService;
import com.xwd.base.model.Mq;

public interface MqService extends BaseService<Mq>{

	public int save(Mq entity);

	public int update(Mq entity);

	public int saveOrUpdate(Mq entity);

	public void delete(Mq entity);
}
