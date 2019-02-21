package com.xwd.base.service.impl;

import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;

import com.xwd.base.model.*;
import com.xwd.base.dao.*;
import com.xwd.base.service.*;

@Component
@Transactional
@Aspect
public class ActivityServiceImpl extends AbstractBaseService<Activity> implements ActivityService{

	@Autowired
	private ActivityDao activityDao;
	
	public EntityDao<Activity,Long> getEntityDao() {
		return this.activityDao;
	}
	
	@Override
	public int save(Activity entity) {
		return activityDao.save(entity);
	}

	@Override
	public int update(Activity entity) {
		return activityDao.update(entity);
	}

	@Override
	public int saveOrUpdate(Activity entity) {
		return activityDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Activity entity) {
		activityDao.delete(entity);
	}

	/**
	 * 查看最新活动
	 */
	public List<Activity> findActivity() {
		return activityDao.findActivity();
	}

	/**
	 * 查看活动列表
	 */
	public List<Activity> findActivityIsNew() {
		return activityDao.findIsNew();
	}
	
	/**
	 * 查找栏目id
	 */
	public Long  findByAcitvId(Long activId){
		return activityDao.findByAcitvId(activId);
	}
	
	
}
