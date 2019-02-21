package com.xwd.base.service;

import com.frame.base.BaseService;
import com.xwd.base.model.SysConfig;


public interface SysConfigService extends BaseService<SysConfig>{
	public int save(SysConfig entity);

	public int update(SysConfig entity);

	public int saveOrUpdate(SysConfig entity);

	public void delete(SysConfig entity);
	
	/**
	 * 根据key获得value
	 * @param key
	 * @return
	 */
	public String getValue(String key);

	public void initCache();
}
