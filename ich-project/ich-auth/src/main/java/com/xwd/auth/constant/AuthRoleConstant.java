package com.xwd.auth.constant;

import java.util.HashMap;
import java.util.Map;

import com.xwd.auth.model.AuthRole;

/**
 * 平台权限
 * 
 * @author ljl
 *
 */
public class AuthRoleConstant {
	// 管理员
	public static final String ROLE_SYSTEM = "system";
	public static final Long ROLE_SYSTEM_ID = 10L;
	// 顾客
	public static final String ROLE_CUSTOMER = "customer";
	public static final Long ROLE_CUSTOMER_ID = 11L;
	//手艺人
	public static final String CRAFTSMAN_CUSTOMER = "craftsman";
	public static final Long ROLE_CRAFTSMAN_ID = 12L;

	// 角色key=id,value=entity
	public static Map<Long, AuthRole> AUTH_ROLE_MAP = new HashMap<Long, AuthRole>();
}
