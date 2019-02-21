package com.xwd.auth.service.impl;

import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.auth.annotation.Roles;
import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.auth.dao.AuthChildrenGroupDao;
import com.xwd.auth.model.AuthChildrenGroup;
import com.xwd.auth.model.AuthGroupPermission;
import com.xwd.auth.service.AuthChildrenGroupService;

/**
 * 
 * @author ljl
 *
 */
@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class AuthChildrenGroupServiceImpl extends AbstractBaseService<AuthChildrenGroup> implements AuthChildrenGroupService{

	@Autowired
	private AuthChildrenGroupDao authChildrenGroupDao;
	
	public EntityDao<AuthChildrenGroup,Long> getEntityDao() {
		return this.authChildrenGroupDao;
	}
	
	/**
	 * 根据组ID删除关系组
	 * @param groupId
	 */
	public void deleteByGroupId(Long groupId) {
		authChildrenGroupDao.deleteByGroupId(groupId);
	}
	
	
	/** 
	 * 根据groupId查询分配的组
	 *  @param groupId
	 **/
	@Roles(value="admin")
	public List<AuthGroupPermission> findByGroupId(Long groupId) {
		return authChildrenGroupDao.findByGroupId(groupId);
	}

	@Override
	public int save(AuthChildrenGroup entity) {
		return authChildrenGroupDao.save(entity);
	}

	@Override
	public int update(AuthChildrenGroup entity) {
		return authChildrenGroupDao.update(entity);
	}

	@Override
	public int saveOrUpdate(AuthChildrenGroup entity) {
		return authChildrenGroupDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(AuthChildrenGroup entity) {
		authChildrenGroupDao.delete(entity);
	}
	
}
