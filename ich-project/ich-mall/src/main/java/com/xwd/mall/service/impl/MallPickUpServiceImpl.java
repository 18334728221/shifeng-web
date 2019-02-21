package com.xwd.mall.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.mall.dao.MallPickUpDao;
import com.xwd.mall.model.MallPickUp;
import com.xwd.mall.service.MallPickUpService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class MallPickUpServiceImpl extends AbstractBaseService<MallPickUp> implements MallPickUpService{

	@Autowired
	private MallPickUpDao mallPickUpDao;
	
	public EntityDao<MallPickUp,Long> getEntityDao() {
		return this.mallPickUpDao;
	}
	
	@Override
	public int save(MallPickUp entity) {
		return mallPickUpDao.save(entity);
	}

	@Override
	public int update(MallPickUp entity) {
		return mallPickUpDao.update(entity);
	}

	@Override
	public int saveOrUpdate(MallPickUp entity) {
		return mallPickUpDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(MallPickUp entity) {
		mallPickUpDao.delete(entity);
	}
	
}
