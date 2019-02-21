package com.xwd.seller.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.seller.dao.CraftsmanDao;
import com.xwd.seller.model.Craftsman;
import com.xwd.seller.service.CraftsmanService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class CraftsmanServiceImpl extends AbstractBaseService<Craftsman> implements CraftsmanService{

	@Autowired
	private CraftsmanDao craftsmanDao;
	
	public EntityDao<Craftsman,Long> getEntityDao() {
		return this.craftsmanDao;
	}
	
	@Override
	public int save(Craftsman entity) {
		return craftsmanDao.save(entity);
	}

	@Override
	public int update(Craftsman entity) {
		return craftsmanDao.update(entity);
	}

	@Override
	public int saveOrUpdate(Craftsman entity) {
		return craftsmanDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Craftsman entity) {
		craftsmanDao.delete(entity);
	}

	@Override
	public Craftsman findLastCraftsman() {
		return craftsmanDao.findLastCraftsman();
	}
	
	/**
	 * 根据手机号或者email获得用户
	 * @param params
	 * @return
	 */
	public Craftsman getCustomerByEmailOrMobile(Object ...params){
		return this.craftsmanDao.getCustomerByEmailOrMobile(params);
	}
	
}
