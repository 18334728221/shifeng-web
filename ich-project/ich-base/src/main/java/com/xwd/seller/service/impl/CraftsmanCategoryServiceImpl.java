package com.xwd.seller.service.impl;

import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;

import com.xwd.seller.model.*;
import com.xwd.seller.dao.*;
import com.xwd.seller.service.*;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class CraftsmanCategoryServiceImpl extends AbstractBaseService<CraftsmanCategory> implements CraftsmanCategoryService{

	@Autowired
	private CraftsmanCategoryDao craftsmanCategoryDao;
	
	public EntityDao<CraftsmanCategory,Long> getEntityDao() {
		return this.craftsmanCategoryDao;
	}
	
	@Override
	public int save(CraftsmanCategory entity) {
		return craftsmanCategoryDao.save(entity);
	}

	@Override
	public int update(CraftsmanCategory entity) {
		return craftsmanCategoryDao.update(entity);
	}

	@Override
	public int saveOrUpdate(CraftsmanCategory entity) {
		return craftsmanCategoryDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(CraftsmanCategory entity) {
		craftsmanCategoryDao.delete(entity);
	}

	/**
	 * 根据craftsmanNo查询分配的产品种类
	 */
	public List<CraftsmanCategory> findByCraftsmanNo(Long craftsmanNo) {
		return craftsmanCategoryDao.findByCraftsmanNo(craftsmanNo);
	}
	
}
