package com.xwd.auth.realm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.auth.Authenticator;
import com.auth.AuthorizationPrincipal;
import com.auth.Group;
import com.auth.User;
import com.auth.common.ExceptionConstant;
import com.auth.constant.RoleConstant;
import com.auth.exception.AuthenticationException;
import com.auth.impl.AbstractAuthRealm;
import com.auth.impl.SimpleAuth;
import com.auth.permission.LevelPermission;
import com.auth.permission.OperationPermission;
import com.auth.spring.util.SpringContextUtil;
import com.xwd.auth.constant.AuthRoleConstant;
import com.xwd.auth.model.AuthGroup;
import com.xwd.auth.model.AuthPermission;
import com.xwd.auth.model.AuthRole;
import com.xwd.auth.model.AuthUser;
import com.xwd.auth.service.AuthGroupService;
import com.xwd.auth.service.AuthPermissionService;
import com.xwd.auth.service.AuthRoleService;
import com.xwd.auth.service.AuthUserService;
import com.xwd.customer.model.Customer;
import com.xwd.customer.service.CustomerService;
import com.xwd.seller.service.CraftsmanService;

/**
 * 权限realm
 * 连接数据库的桥梁
 * @author Administrator
 *
 * @param <ID>
 */
public class MybatisAuthRealm<ID extends Serializable> extends AbstractAuthRealm<Long>{

	public MybatisAuthRealm() {
		super();
	}

	public User<Long> findUserByPrincipal(String name) {
		AuthUserService authUserService = (AuthUserService)SpringContextUtil.getApplicationContext().getBean(AuthUserService.class);
		User<Long> user = authUserService.getUserByLoginNameOrEmailOrMobile("principal", name);
		if(user == null){
			CustomerService customerService = (CustomerService)SpringContextUtil.getApplicationContext().getBean(CustomerService.class);
			user = customerService.getCustomerByEmailOrMobile("principal", name);
		}
		return user;
	}
	
	@Override
	public User<Long> findUserByPrincipalPassword(String name, String password) {
		Authenticator authenticator = (Authenticator)SpringContextUtil.getApplicationContext().getBean(Authenticator.class);
		AuthUserService authUserService = (AuthUserService)SpringContextUtil.getApplicationContext().getBean(AuthUserService.class);
		User<Long> user = authUserService.getUserByLoginNameOrEmailOrMobile("principal", name);
		boolean isUser = false;
		if(user != null){
			isUser = authenticator.credentialsMatch(password, user.getPassword());
			if(!isUser){
				user = null;
			}
		}
		if(user == null){
			CustomerService customerService = (CustomerService)SpringContextUtil.getApplicationContext().getBean(CustomerService.class);
			user = customerService.getCustomerByEmailOrMobile("principal", name);
			if(user != null){
				isUser = authenticator.credentialsMatch(password, user.getPassword());
				if(!isUser){
					user = null;
				}
			}
		}
		if(user == null){
			CraftsmanService craftsmanService = (CraftsmanService)SpringContextUtil.getApplicationContext().getBean(CraftsmanService.class);
			user = craftsmanService.getCustomerByEmailOrMobile("principal", name);
			if(user != null){
				isUser = authenticator.credentialsMatch(password, user.getPassword());
				if(!isUser){
					user = null;
				}
			}
		}
		return user;
	}

	public User<Long> findUserByPrincipal(String principal, String role) {
		if(AuthRoleConstant.ROLE_CUSTOMER.equals(role)){
			CustomerService customerService = (CustomerService)SpringContextUtil.getApplicationContext().getBean(CustomerService.class);
			return customerService.getCustomerByEmailOrMobile("principal", principal);
		}else if(AuthRoleConstant.CRAFTSMAN_CUSTOMER.equals(role)){
			CraftsmanService craftsmanService = (CraftsmanService)SpringContextUtil.getApplicationContext().getBean(CraftsmanService.class);
			return craftsmanService.getCustomerByEmailOrMobile("principal", principal);
		}else {
			AuthUserService authUserService = (AuthUserService)SpringContextUtil.getApplicationContext().getBean(AuthUserService.class);
			return authUserService.getUserByLoginNameOrEmailOrMobile("principal", principal);
		}
	}
	
	public User<Long> findUserByPrincipal(String name, Long areaPlatMark) {
		AuthUserService authUserService = (AuthUserService)SpringContextUtil.getApplicationContext().getBean(AuthUserService.class);
		User<Long> user = authUserService.getUserByLoginNameOrEmailOrMobile("principal", name, "areaPlatMark", areaPlatMark);
		if(user == null){
			CraftsmanService craftsmanService = (CraftsmanService)SpringContextUtil.getApplicationContext().getBean(CraftsmanService.class);
			user = craftsmanService.getCustomerByEmailOrMobile("principal", name, "areaPlatMark", areaPlatMark);
		}
		if(user == null){
			CustomerService customerService = (CustomerService)SpringContextUtil.getApplicationContext().getBean(CustomerService.class);
			user = customerService.getCustomerByEmailOrMobile("principal", name, "areaPlatMark", areaPlatMark);
		}
		return user;
	}
	
	public AuthorizationPrincipal findUserAuthorizationPrincipal(User<Long> user) {
		AuthRoleService authRoleService = (AuthRoleService)SpringContextUtil.getApplicationContext().getBean(AuthRoleService.class);
		AuthGroupService authGroupService = (AuthGroupService)SpringContextUtil.getApplicationContext().getBean(AuthGroupService.class);
		AuthPermissionService authPermissionService = (AuthPermissionService)SpringContextUtil.getApplicationContext().getBean(AuthPermissionService.class);
		SimpleAuth simpleAuth = new SimpleAuth();
		simpleAuth.addRole(RoleConstant.ROLE_FRONT_VISITOR);
		if(user instanceof Customer){
			simpleAuth.addRole(AuthRoleConstant.ROLE_CUSTOMER);
			//根据角色加入对应的权限
			List<String> roles = new ArrayList<String>();
			for(String str : simpleAuth.getRoles()){
				roles.add(str);
			}
			if(!roles.isEmpty()){
				List<AuthPermission> permissionList = authPermissionService.findPermissionsByRoles(roles);
				for (AuthPermission permission : permissionList) {
					if(StringUtils.isNotBlank(permission.getOperation())){
						 if(permission.getPermissionType() == AuthPermission.PERMISSION_TYPE_OPERATION){
			            	 OperationPermission operationPermission = OperationPermission.getOperationPermission(permission.getTarget(),permission.getOperation());
			            	 simpleAuth.addOperationPermission(operationPermission);
			             }else{
			            	 LevelPermission levelPermission = LevelPermission.getLevelPermission(permission.getTarget(),Integer.valueOf(permission.getOperation()));
			            	 simpleAuth.addLevelPermission(levelPermission);
			             }
					}
				}
			}
		} else {
			List<AuthRole> roleList = authRoleService.findRolesByUser(user.getId());
			for (AuthRole role : roleList) {
				simpleAuth.addRole(role.getRoleName());
			}
			
			List<AuthPermission> permissionList = authPermissionService.findPermissionsByUser(user.getId());
			for (AuthPermission permission : permissionList) {
				if(StringUtils.isNotBlank(permission.getOperation())){
		            if(permission.getPermissionType() == AuthPermission.PERMISSION_TYPE_OPERATION){
		            	OperationPermission operationPermission = OperationPermission.getOperationPermission(permission.getTarget(),permission.getOperation());
		            	simpleAuth.addOperationPermission(operationPermission);
		            }else{
		            	LevelPermission levelPermission = LevelPermission.getLevelPermission(permission.getTarget(),Integer.valueOf(permission.getOperation()));
		            	simpleAuth.addLevelPermission(levelPermission);
		            }
				}
			}
			List<AuthGroup> groupList = authGroupService.findGroupsByUser(user.getId());
			Set<String> groupNames = new HashSet<String>();
			for (AuthGroup group : groupList) {
				simpleAuth.addGroup(group);
				//添加子group
				addSubGroup(group,groupNames,simpleAuth);			
			}		
			//根据角色加入对应的权限
			List<String> roles = new ArrayList<String>();
			for(String str : simpleAuth.getRoles()){
				roles.add(str);
			}
			if(!roles.isEmpty()){
				permissionList = authPermissionService.findPermissionsByRoles(roles);
				for (AuthPermission permission : permissionList) {
					if(StringUtils.isNotBlank(permission.getOperation())){
						 if(permission.getPermissionType() == AuthPermission.PERMISSION_TYPE_OPERATION){
			            	 OperationPermission operationPermission = OperationPermission.getOperationPermission(permission.getTarget(),permission.getOperation());
			            	 simpleAuth.addOperationPermission(operationPermission);
			             }else{
			            	 LevelPermission levelPermission = LevelPermission.getLevelPermission(permission.getTarget(),Integer.valueOf(permission.getOperation()));
			            	 simpleAuth.addLevelPermission(levelPermission);
			             }
					}
				}
			}
		}
		return simpleAuth;
	}
	
	/**
	 * 添加子group
	 * @param group
	 * @param groupNames
	 * @param account
	 */
	private void addSubGroup(AuthGroup group , Set<String> groupNames,SimpleAuth simpleAuth){
		AuthRoleService authRoleService = (AuthRoleService)SpringContextUtil.getApplicationContext().getBean(AuthRoleService.class);
		AuthGroupService authGroupService = (AuthGroupService)SpringContextUtil.getApplicationContext().getBean(AuthGroupService.class);
		AuthPermissionService authPermissionService = (AuthPermissionService)SpringContextUtil.getApplicationContext().getBean(AuthPermissionService.class);
		List<AuthRole> roleList = authRoleService.findRolesByGroup(group.getId());
		for (AuthRole role : roleList) {
			simpleAuth.addRole(role.getRoleName());
			group.addRole(role.getRoleName());
		}
		List<AuthPermission> permissionList = authPermissionService.findPermissionsByGroup(group.getId());
		for (AuthPermission permission : permissionList) {
			if(StringUtils.isNotBlank(permission.getOperation())){
	            if(permission.getPermissionType() == AuthPermission.PERMISSION_TYPE_OPERATION){
	            	OperationPermission operationPermission = OperationPermission.getOperationPermission(permission.getTarget(),permission.getOperation());
	            	simpleAuth.addOperationPermission(operationPermission);
	            }else{
	            	LevelPermission levelPermission = LevelPermission.getLevelPermission(permission.getTarget(),Integer.valueOf(permission.getOperation()));
	            	simpleAuth.addLevelPermission(levelPermission);
	            }
			}
		}
		//是否已经做过处理
		if(groupNames.contains(group.getName()))
			return;
		groupNames.add(group.getName());
		//目前不需要支持组包含组的功能
		List<AuthGroup> list = authGroupService.findSubGroupsByGroup(group.getId());
		if(list.isEmpty()){
			return;
		}else{
			for(AuthGroup g : list){
				group.addGroup(g);
				addSubGroup(g, groupNames, simpleAuth);
			}
		}
	}

	public Collection<OperationPermission> queryOperationPermissionByUser(User<Long> user) {
		AuthPermissionService authPermissionService = (AuthPermissionService)SpringContextUtil.getApplicationContext().getBean(AuthPermissionService.class);
		AuthUser obj = (AuthUser)user;
		Collection<OperationPermission> collection = new HashSet<OperationPermission>();
		List<AuthPermission> permissionList = authPermissionService.findPermissionsByUserAndType(obj.getId(), AuthPermission.PERMISSION_TYPE_OPERATION);
		for (AuthPermission permission : permissionList) {
        	OperationPermission operationPermission = OperationPermission.getOperationPermission(permission.getTarget(),permission.getOperation());
        	collection.add(operationPermission);
		}
		return collection;
	}

	public Collection<LevelPermission> queryLevelPermissionByUser(User<Long> user) {
		AuthPermissionService authPermissionService = (AuthPermissionService)SpringContextUtil.getApplicationContext().getBean(AuthPermissionService.class);
		AuthUser obj = (AuthUser)user;
		Collection<LevelPermission> collection = new HashSet<LevelPermission>();
		List<AuthPermission> permissionList = authPermissionService.findPermissionsByUserAndType(obj.getId(), AuthPermission.PERMISSION_TYPE_LEVEL);
		for (AuthPermission permission : permissionList) {
        	LevelPermission levelPermission = LevelPermission.getLevelPermission(permission.getTarget(),Integer.valueOf(permission.getOperation()));
        	collection.add(levelPermission);            
		}
		return collection;
	}

	public Collection<String> queryRoleByUser(User<Long> user) {
		AuthRoleService authRoleService = (AuthRoleService)SpringContextUtil.getApplicationContext().getBean(AuthRoleService.class);
		AuthUser obj = (AuthUser)user;
		Collection<String> collection = new HashSet<String>();
		List<AuthRole> roleList = authRoleService.findRolesByUser(obj.getId());
		for (AuthRole role : roleList) {
			collection.add(role.getRoleName());
		}
		return collection;
	}

	public Collection<String> queryGroupByUser(User<Long> user) {
		AuthGroupService authGroupService = (AuthGroupService)SpringContextUtil.getApplicationContext().getBean(AuthGroupService.class);
		AuthUser obj = (AuthUser)user;
		Collection<String> collection = new HashSet<String>();
		List<AuthGroup> groupList = authGroupService.findGroupsByUser(obj.getId());
		for (AuthGroup group : groupList) {
			collection.add(group.getName());
		}		
		return collection;
	}

	/**
	 * 后台获取
	 */
	public Collection<Group> queryAllGroups() {
		AuthGroupService authGroupService = (AuthGroupService)SpringContextUtil.getApplicationContext().getBean(AuthGroupService.class);
		List<Group> groupList = new ArrayList<Group>();
		List<AuthGroup> list =  authGroupService.findAll();	
		for(AuthGroup group : list){
			addSubGroup(group);
		}
		groupList.addAll(list);
		return groupList;
	}
	
	/**
	 * 添加子group
	 * @param group
	 * @param groupNames
	 */
	private void addSubGroup(AuthGroup group){
		AuthGroupService authGroupService = (AuthGroupService)SpringContextUtil.getApplicationContext().getBean(AuthGroupService.class);
		List<AuthGroup> list = authGroupService.findSubGroupsByGroup(group.getId());
		if(list.isEmpty()){
			return;
		}else{
			for(AuthGroup g : list){
				group.addGroup(g);
			}
		}
	}
	

	/**
	 * 后台获取 从配置文件中获得
	 */
	public Collection<String> queryAllRoles() {
		AuthRoleService authRoleService = (AuthRoleService)SpringContextUtil.getApplicationContext().getBean(AuthRoleService.class);
		List<AuthRole> roleList = authRoleService.findAll();
		Collection<String> roles = new HashSet<String>();
		for (AuthRole role : roleList) {
			roles.add(role.getRoleName());
		}		
		return roles;
	}

	public User<Long> findUserById(Long id) {
		AuthUserService authUserService = (AuthUserService)SpringContextUtil.getApplicationContext().getBean(AuthUserService.class);
		User<Long> user = authUserService.get(id);
		if(user == null)
			throw new AuthenticationException(ExceptionConstant.EXCEPTION_USERNAME_ERROR);			
		return user;
	}

	public User<Long> findUserByRole(String role) {
		AuthUserService authUserService = (AuthUserService)SpringContextUtil.getApplicationContext().getBean(AuthUserService.class);
		AuthRoleService authRoleService = (AuthRoleService)SpringContextUtil.getApplicationContext().getBean(AuthRoleService.class);
		AuthRole authRole = authRoleService.findUniqueBy("roleName", role);
		if(authRole == null){
			return null;
		}
		List<AuthUser> list = authUserService.findUserByRole(authRole.getId());
		if(list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}

}
