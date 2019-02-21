package com.xwd.auth.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthUser;

/**
 * @author sf
 */

@Component
public class AuthUserDao extends BaseMyIbatisDao<AuthUser, Long> {

	public Class<AuthUser> getEntityClass() {
		return AuthUser.class;
	}
	
	
	
	public int saveOrUpdate(AuthUser entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<AuthUser> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	public AuthUser getUserByLoginNameOrEmailOrMobile(Object ...params) {
		return db().selectOne(getEntityClass().getSimpleName() + ".getUserByLoginNameOrEmailOrMobile", map(params));
	}
	

	/**
	 * 根据课程获得通知用户
	 * @param params
	 * @return
	 */
	public List<AuthUser> findUserByLesson(Object ...params){
		 return db().selectList(getEntityClass().getSimpleName() + ".findUserByLesson",  map(params));
	}
	
	/**
	 * 根据类型、学校和名称查询老师
	 * @param pageRequest
	 * @return
	 */
	public Page<AuthUser> findTeachersBySchoolName(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery(getEntityClass().getSimpleName() + ".findTeachersBySchoolName", pageRequest);
	}
	
	/**
	 * 根据地区id获取该地区下一级地区所有老师
	 * @param params
	 * @return
	 */
	public List<AuthUser> findNextAreaTeachersByAreaID(Object ...params){
		return db().selectList("Teacher.findNextAreaTeachersByAreaID",  map(params));
	}

	/**
	 * 根据地区、名称查询老师
	 * @param pageRequest
	 * @return
	 */
	public Page<AuthUser> findTeachersByAreaName(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery(getEntityClass().getSimpleName() + ".findTeachersByArea", pageRequest);
	}

	/**
	 * 查找除了userId以外的所有user列表
	 * @param pageRequest
	 * @return
	 */
	public Page<AuthUser> findExceptByUserIdPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery(getEntityClass().getSimpleName() + ".findExceptByUserIdPageRequest", pageRequest);
	}
	
	public List<AuthUser> findExceptByUserId(Long userId) {
		return db().selectList(getEntityClass().getSimpleName() + ".findExceptByUserIdPageRequest", userId);
	}

	/**
	 * 根据角色获得用户
	 * @param roleId
	 * @return
	 */
	public List<AuthUser> findUserByRole(Long roleId) {
		return db().selectList(getEntityClass().getSimpleName() + ".findUserByRole", roleId);
	}


	public Page<AuthUser> findTeachersByName(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery(getEntityClass().getSimpleName() + ".findTeachersByName", pageRequest);
	}
	
	//通过用户名获取密码
	public String findPasswordByName(String name) {
		return db().selectOne(getEntityClass().getSimpleName() + ".findPasswordByName",name);
	}
	
	//通过用户名查找登录信息
	public AuthUser findAuthUserByName(String name) {
		return db().selectOne(getEntityClass().getSimpleName() + ".findAuthUserByName",name);
	}
}
