package com.xwd.base.service.impl;

import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.base.dao.FestivalDao;
import com.xwd.base.model.Festival;
import com.xwd.base.provider.FestivalProvider;
import com.xwd.base.service.FestivalService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class FestivalServiceImpl extends AbstractBaseService<Festival> implements FestivalService{

	@Autowired
	private FestivalDao festivalDao;
	@Autowired
	private FestivalProvider festivalProvider;
	
	public EntityDao<Festival,Long> getEntityDao() {
		return this.festivalDao;
	}
	
	@Override
	public int save(Festival entity) {
		int n = festivalDao.save(entity);
		this.festivalProvider.saveOrUpdate(entity);
		return n;
	}

	@Override
	public int update(Festival entity) {
		int n = festivalDao.update(entity);
		this.festivalProvider.saveOrUpdate(entity);
		return n;
	}

	@Override
	public int saveOrUpdate(Festival entity) {
		int n = festivalDao.saveOrUpdate(entity);
		this.festivalProvider.saveOrUpdate(entity);
		return n;
	}

	@Override
	public void delete(Festival entity) {
		festivalDao.delete(entity);
		festivalProvider.delete(entity.getId());
	}
	
	@Override
	public void deleteByIds(String ids){
		this.festivalDao.deleteByIds(ids);
		festivalProvider.deleteByIds(ids);
	}
	
	/**
	 * 
	 */
	public List<Festival> findIsFestival(){
		return festivalDao.findIsFestival();
	}

	@Override
	public void initCache() {
		List<Festival> list = this.findAll();
		for(Festival entity : list){
			this.festivalProvider.saveOrUpdate(entity);
		}
	}
	
}
