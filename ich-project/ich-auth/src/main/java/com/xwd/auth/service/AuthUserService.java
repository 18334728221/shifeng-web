package com.xwd.auth.service;

import java.util.HashMap;
import java.util.List;

import com.frame.base.BaseService;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthUser;


public interface AuthUserService extends BaseService<AuthUser>{

	public int save(AuthUser entity);

	public int update(AuthUser entity);

	public int saveOrUpdate(AuthUser entity);

	public void delete(AuthUser entity);
	

	/**
	 * 根据用户名、邮箱或者手机号
	 * 平台标识获得用户
	 * @param params
	 * @return
	 */
	public AuthUser getUserByLoginNameOrEmailOrMobile(Object ...params);
	
	/**
	 * 根据课程获得通知用户
	 * @param params
	 * @return
	 */
	public List<AuthUser> findUserByLesson(Long lessonId);
	
	/**
	 * 根据类型、学校和名称查询老师
	 * @param pageRequest
	 * @return
	 */
	public Page<AuthUser> findTeachersBySchoolName(PageRequest<HashMap<String, Object>> pageRequest);
	
	/**
	 * 根据地区id获取该地区下一级地区所有老师
	 * @param params
	 * @return
	 */
	public List<AuthUser> findNextAreaTeachersByAreaID(Object ...params);
	
	/**
	 * 根据地区、名称查询老师
	 * userType:用户类型
	 * name:名称
	 * areaPlatMark:地区标识
	 * nextAreaPlatMark:下一个地区标识
	 * @param pageRequest
	 * @return
	 */
	public Page<AuthUser> findTeachersByAreaName(PageRequest<HashMap<String, Object>> pageRequest);


	/**
	 * 查找除了userId以外的所有user列表
	 * @param pageRequest
	 * @return
	 */
	public Page<AuthUser> findExceptByUserIdPageRequest(PageRequest<HashMap<String, Object>> pageRequest);
	
	public List<AuthUser> findExceptByUserId(Long roleId);
	
	/**
	 * 根据角色获得用户
	 * @param roleId
	 * @return
	 */
	public List<AuthUser> findUserByRole(Long roleId);

	public Page<AuthUser> findTeachersByName(PageRequest<HashMap<String, Object>> pageRequest);

	public String findPasswordByName(String name);
	
	public AuthUser findAuthUserByName(String name);

}
