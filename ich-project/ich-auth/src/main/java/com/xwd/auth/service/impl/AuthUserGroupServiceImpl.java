package com.xwd.auth.service.impl;

import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.auth.dao.AuthUserGroupDao;
import com.xwd.auth.model.AuthUserGroup;
import com.xwd.auth.service.AuthUserGroupService;

/**
 * 
 * @author ljl
 *
 */
@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class AuthUserGroupServiceImpl extends AbstractBaseService<AuthUserGroup> implements AuthUserGroupService{

	@Autowired
	private AuthUserGroupDao authUserGroupDao;
	
	public EntityDao<AuthUserGroup,Long> getEntityDao() {
		return this.authUserGroupDao;
	}
	
	/**
	 * 根据userId删除组
	 * @param userId
	 */
	public void deleteByUserId(Long userId) {
		authUserGroupDao.deleteByUserId(userId);
	}
	
	/** 
	 * 根据userId查询分配的组
	 * @param userId
	 **/
	public List<AuthUserGroup> findByUserId(Long userId) {
		return authUserGroupDao.findByUserId(userId);
	}

	@Override
	public int save(AuthUserGroup entity) {
		return authUserGroupDao.save(entity);
	}

	@Override
	public int update(AuthUserGroup entity) {
		return authUserGroupDao.update(entity);
	}

	@Override
	public int saveOrUpdate(AuthUserGroup entity) {
		return authUserGroupDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(AuthUserGroup entity) {
		authUserGroupDao.delete(entity);
	}
	
}
