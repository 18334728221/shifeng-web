package com.xwd.auth.service;

/**
 * 权限模块组合接口
 * @author ljl
 *
 */
public interface AuthComprisedService {

	/**
	 * 获得模块和权限列表
	 * @return 模块和权限组成的json字符串树
	 */
	public String findModulePermissionAsJson();
	
	/**
	 * 获得模块列表
	 * @return 模块组成的json字符串树
	 */
	public String findModuleAsJson();
}
