package com.xwd.base.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.base.dao.MqThemeDao;
import com.xwd.base.model.MqTheme;
import com.xwd.base.service.MqThemeService;
	

/**
 * @author ljl
 */
@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class MqThemeServiceImpl extends AbstractBaseService<MqTheme> implements MqThemeService{

	@Autowired
	private MqThemeDao mqThemeDao;
	
	public EntityDao<MqTheme,Long> getEntityDao() {
		return this.mqThemeDao;
	}
	
	@Override
	public int save(MqTheme entity) {
		return mqThemeDao.save(entity);
	}

	@Override
	public int update(MqTheme entity) {
		return mqThemeDao.update(entity);
	}

	@Override
	public int saveOrUpdate(MqTheme entity) {
		return mqThemeDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(MqTheme entity) {
		mqThemeDao.delete(entity);
	}
	
}
