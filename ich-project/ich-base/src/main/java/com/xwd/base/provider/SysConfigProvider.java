package com.xwd.base.provider;

import java.util.List;

import com.xwd.base.model.SysConfig;

public interface SysConfigProvider {

	public void saveOrUpdate(SysConfig entity);
	
	public void delete(String key);
	
	public SysConfig get(String key);
	
	public String getValue(String key);
	
	public List<SysConfig> findAll();
	
	public void delete(Long id);
	
	public void deleteByIds(String ids);
	
	/**
	 * 是否交易时间
	 * @return
	 */
	public Boolean isTradeTime();
}
