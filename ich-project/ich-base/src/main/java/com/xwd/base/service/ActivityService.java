package com.xwd.base.service;

import java.util.List;

import com.frame.base.BaseService;
import com.xwd.base.model.Activity;


public interface ActivityService extends BaseService<Activity>{

	public int save(Activity entity);

	public int update(Activity entity);

	public int saveOrUpdate(Activity entity);

	public void delete(Activity entity);
	
	/**
	 * 查看活动列表
	 */
	public List<Activity> findActivity();
	
	public Long  findByAcitvId(Long activId);
	/**
	 * 查看活动列表
	 */
	public List<Activity> findActivityIsNew();
	
}
