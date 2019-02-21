package com.xwd.msg.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.msg.dao.CraftsmanLogisticsDao;
import com.xwd.msg.model.CraftsmanLogistics;
import com.xwd.msg.service.CraftsmanLogisticsService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class CraftsmanLogisticsServiceImpl extends AbstractBaseService<CraftsmanLogistics> implements CraftsmanLogisticsService{

	@Autowired
	private CraftsmanLogisticsDao craftsmanLogisticsDao;
	
	public EntityDao<CraftsmanLogistics,Long> getEntityDao() {
		return this.craftsmanLogisticsDao;
	}
	
	@Override
	public int save(CraftsmanLogistics entity) {
		return craftsmanLogisticsDao.save(entity);
	}

	@Override
	public int update(CraftsmanLogistics entity) {
		return craftsmanLogisticsDao.update(entity);
	}

	@Override
	public int saveOrUpdate(CraftsmanLogistics entity) {
		return craftsmanLogisticsDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(CraftsmanLogistics entity) {
		craftsmanLogisticsDao.delete(entity);
	}
	
}
