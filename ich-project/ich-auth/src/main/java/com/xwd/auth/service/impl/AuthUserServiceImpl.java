package com.xwd.auth.service.impl;

import java.util.HashMap;
import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.dao.AuthUserDao;
import com.xwd.auth.model.AuthUser;
import com.xwd.auth.service.AuthUserService;

/**
 * @author ljl
 */
@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class AuthUserServiceImpl extends AbstractBaseService<AuthUser> implements AuthUserService{

	@Autowired
	private AuthUserDao authUserDao;
	
	public EntityDao<AuthUser,Long> getEntityDao() {
		return this.authUserDao;
	}
	
	@Override
	public int save(AuthUser entity) {
		return authUserDao.save(entity);
	}

	@Override
	public int update(AuthUser entity) {
		return authUserDao.update(entity);
	}

	@Override
	public int saveOrUpdate(AuthUser entity) {
		return authUserDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(AuthUser entity) {
		authUserDao.delete(entity);
	}
	
	/**
	 * 根据用户名、邮箱或者手机号
	 * 平台标识获得用户
	 * @param params
	 * @return
	 */
	public AuthUser getUserByLoginNameOrEmailOrMobile(Object ...params){
		return authUserDao.getUserByLoginNameOrEmailOrMobile(params);
	}
	
	/**
	 * 根据课程获得通知用户
	 * @param params
	 * @return
	 */
	public List<AuthUser> findUserByLesson(Long lessonId){
		return this.authUserDao.findUserByLesson("lessonId", lessonId);
	}
	
	/**
	 * 根据类型、学校和名称查询老师
	 * @param pageRequest
	 * @return
	 */
	public Page<AuthUser> findTeachersBySchoolName(PageRequest<HashMap<String, Object>> pageRequest) {
		return ((AuthUserService) this.authUserDao).findTeachersBySchoolName(pageRequest);
	}
	
	/**
	 * 根据地区id获取该地区下一级地区所有老师
	 * @param params
	 * @return
	 */
	public List<AuthUser> findNextAreaTeachersByAreaID(Object ...params){
		return this.authUserDao.findNextAreaTeachersByAreaID(params);
	}
	
	/**
	 * 根据地区、名称查询老师
	 * userType:用户类型
	 * name:名称
	 * areaPlatMark:地区标识
	 * nextAreaPlatMark:下一个地区标识
	 * @param pageRequest
	 * @return
	 */
	public Page<AuthUser> findTeachersByAreaName(PageRequest<HashMap<String, Object>> pageRequest) {
		return this.authUserDao.findTeachersByAreaName(pageRequest);
	}


	/**
	 * 查找除了userId以外的所有user列表
	 * @param pageRequest
	 * @return
	 */
	public Page<AuthUser> findExceptByUserIdPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return this.authUserDao.findExceptByUserIdPageRequest(pageRequest);
	}
	
	/**
	 * 根据角色获得用户
	 * @param roleId
	 * @return
	 */
	public List<AuthUser> findUserByRole(Long roleId) {
		return this.authUserDao.findUserByRole(roleId);
	}

	public Page<AuthUser> findTeachersByName(PageRequest<HashMap<String, Object>> pageRequest) {
		return authUserDao.findTeachersByName(pageRequest);
	}

	@Override
	public List<AuthUser> findExceptByUserId(Long roleId) {
		return authUserDao.findExceptByUserId(roleId);
	}

	@Override
	public String findPasswordByName(String name) {
		return authUserDao.findPasswordByName(name);		
	}

	@Override
	public AuthUser findAuthUserByName(String name) {
		return authUserDao.findAuthUserByName(name);	
	}
}
