package com.xwd.base.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.base.dao.BannerDao;
import com.xwd.base.model.Banner;
import com.xwd.base.service.BannerService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class BannerServiceImpl extends AbstractBaseService<Banner> implements BannerService{

	@Autowired
	private BannerDao bannerDao;
	
	
	public EntityDao<Banner,Long> getEntityDao() {
		return this.bannerDao;
	}
	
	@Override
	public int save(Banner entity) {
		return bannerDao.save(entity);
	}

	@Override
	public int update(Banner entity) {
		return bannerDao.update(entity);
	}

	@Override
	public int saveOrUpdate(Banner entity) {
		return bannerDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Banner entity) {
		bannerDao.delete(entity);
	}
}
