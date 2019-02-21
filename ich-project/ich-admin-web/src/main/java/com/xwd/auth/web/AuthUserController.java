package com.xwd.auth.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth.Authenticator;
import com.auth.constant.RoleConstant;
import com.auth.provider.ForwardingAuthRealm;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.frame.util.CollectionUtils;
import com.xwd.auth.model.AuthUser;
import com.xwd.auth.model.AuthUserGroup;
import com.xwd.auth.model.AuthUserPermission;
import com.xwd.auth.model.AuthUserRelation;
import com.xwd.auth.model.AuthUserRole;
import com.xwd.auth.service.AuthUserGroupService;
import com.xwd.auth.service.AuthUserPermissionService;
import com.xwd.auth.service.AuthUserRelationService;
import com.xwd.auth.service.AuthUserRoleService;
import com.xwd.auth.service.AuthUserService;
import com.xwd.base.constant.MessageConstant;
import com.xwd.base.util.BaseDataUtil;
import com.xwd.base.web.BaseAdminController;
import com.xwd.log.service.LogService;


/**
 * 用户控制层
 * @author ljl
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin/auth/user")
public class AuthUserController extends BaseAdminController {

	@Autowired
	private AuthUserService authUserService;
	@Autowired
	private AuthUserGroupService authUserGroupService;
	@Autowired
	private AuthUserRoleService authUserRoleService;
	@Autowired
	private AuthUserPermissionService authUserPermissionService;
	@Autowired
	private AuthUserRelationService authUserRelationService;
	@Autowired
	private LogService logService;
	@Autowired
	private Authenticator authenticator;
	@Autowired
	private ForwardingAuthRealm<Long> realm;

	@RequestMapping("/index")
	public String index(){
		return "admin/auth/user";
	}

	/**
	 * 执行搜索 .
	 **/
	@RequestMapping("/findAll")
	@ResponseBody
	public void findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Page<AuthUser> page = authUserService.findByPageRequest(pageRequest);
		this.outPageJson(response, page, true);
	}

	/**
	 * 根据类型查找
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/findUserByType")
	@ResponseBody
	public void findUserByType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue( request);
		Map mapFilters = pageRequest.getFilters();
		String name = get("name");
		if(StringUtils.isNotBlank(name)){
			mapFilters.put("name", name);
			mapFilters.put("trueName", name);
		}
		Long userType = getLong("userType");
		mapFilters.put("userType", userType);
		Page page = authUserService.findByPageRequest(pageRequest);
		this.outPageJson(response, page, false);
	}


	/**
	 * 保存对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AuthUser authUser = null;
		Long id = getLong("id");
		if(null != id){
			authUser = authUserService.get(id);
			//清除缓存，修改的时候
			this.realm.deleteUser(authUser);
			authUser.setUpdateTime(Calendar.getInstance());
			this.setFieldValues(authUser, request, true);
			BaseDataUtil.setFieldName(authUser);
			if(StringUtils.isNotEmpty(authUser.getName())){
				AuthUser validatorName = authUserService.getUserByLoginNameOrEmailOrMobile("principal",authUser.getName(),"id",id);
				if(null != validatorName){
					this.outResultJsoni18n(response, MessageConstant.USER_VALIDATOR_NAME,"name");
					return;
				}
			}
			if(StringUtils.isNotEmpty(authUser.getMobile())){
				AuthUser validatorMobile = authUserService.getUserByLoginNameOrEmailOrMobile("principal",authUser.getMobile(),"id",id);
				if(null != validatorMobile){
					this.outResultJsoni18n(response, MessageConstant.USER_VALIDATOR_MOBILE,"mobile");
					return;
				}
			}
			if(StringUtils.isNotEmpty(authUser.getEmail())){
				AuthUser validatorEmail = authUserService.getUserByLoginNameOrEmailOrMobile("principal",authUser.getEmail(),"id",id);
				if(null != validatorEmail){
					this.outResultJsoni18n(response, MessageConstant.USER_VALIDATOR_EMAIL,"email");
					return;
				}
			}
			authUserService.update(authUser);
			authUserRoleService.deleteBy("userId", authUser.getId());
			logService.add(request, "更新了用户操作");
		}else{
			authUser = new AuthUser();
			this.setFieldValues(authUser, request, false);
			BaseDataUtil.setFieldName(authUser);
			authUser.setLocalId(authenticator.generateId());
			String password = authenticator.encodeCredentials("888888");
			if(null != password){
				authUser.setPassword(password);
			}
			authUser.setCreateTime(Calendar.getInstance());
			if(StringUtils.isNotEmpty(authUser.getName())){
				AuthUser validatorName = authUserService.getUserByLoginNameOrEmailOrMobile("principal",authUser.getName());
				if(null != validatorName){
					this.outResultJsoni18n(response, MessageConstant.USER_VALIDATOR_NAME,"name");
					return;
				}
			}
			if(StringUtils.isNotEmpty(authUser.getMobile())){
				AuthUser validatorMobile = authUserService.getUserByLoginNameOrEmailOrMobile("principal",authUser.getMobile());
				if(null != validatorMobile){
					this.outResultJsoni18n(response, MessageConstant.USER_VALIDATOR_MOBILE,"mobile");
					return;
				}
			}
			if(StringUtils.isNotEmpty(authUser.getEmail())){
				AuthUser validatorEmail = authUserService.getUserByLoginNameOrEmailOrMobile("principal",authUser.getEmail());
				if(null != validatorEmail){
					this.outResultJsoni18n(response, MessageConstant.USER_VALIDATOR_EMAIL,"email");
					return;
				}
			}
			authUserService.save(authUser);
			
			authUser = authUserService.findUniqueBy("name",authUser.getName());

			AuthUserRole authUserRole = new AuthUserRole();
			authUserRole.setUserId(authUser.getId());
			authUserRole.setRoleId(RoleConstant.ROLE_FRONT_VISITOR_ID);
			authUserRoleService.save(authUserRole);
			logService.add(request, "添加了用户操作");
		}
		this.outSuccessJson(response);
	}


	/**
	 * 删除对象.
	 **/
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = get("id");
		if(StringUtils.isNotBlank(id)){
			String [] ids = id.split(",");
			Long userId = Long.parseLong(ids[0]);
			AuthUser user = authUserService.get(userId);
			user.setStatus(3);
			try{
					if(user.getName().equals("admin")){
						this.outFailureJson(response);
					}else{
						authUserService.update(user);
						this.realm.deleteUser(user);
						this.outSuccessJson(response);
					}
			}catch(Exception e){
				e.getStackTrace();
			}
		}
		logService.add(request, "删除了用户操作");
		
	}

	/**
	 * 更新状态.
	 **/
	@RequestMapping("/updateStatus")
	@ResponseBody
	public void updateStatus(HttpServletRequest request, HttpServletResponse response){
		try {
			Long id = getLong("id");
			if(null != id){
				AuthUser user = authUserService.get(id);
				if(get("type").equals("isuse")){
					user.setStatus(1);
				}else{
					user.setStatus(3);
					this.realm.deleteUser(user);
				}
				authUserService.update(user);
				this.outSuccessJson(response);
			}else{
				this.outFailureJson(response);
			}
			logService.add(request, "更新了用户状态操作");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	/**
	 * 更新密码
	 * @param pwd
	 * @param newPwd
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updatePwd")
	@ResponseBody
	public void updatePwd(HttpServletRequest request, HttpServletResponse response)throws Exception{
		AuthUser authUser = null;
		Long id = getLong("id");
		if(null != id){
			authUser = authUserService.get(id);
		}
		if(null != authUser){
			String oldpassword = get("oldpassword");
			if(null !=oldpassword){
				if(authenticator.credentialsMatch(oldpassword, authUser.getPassword())){
					String newpassword = get("newpassword");
					if(null != newpassword){
						String pwd = authenticator.encodeCredentials(newpassword);
						authUser.setPassword(pwd);
						authUserService.update(authUser);
					}
					this.outSuccessJson(response);
				}else{
					this.outFailureJson(response);
				}
			}
		}
		logService.add(request, "更新了密码操作");
		//清除缓存
		this.realm.deleteUser(authUser);
		this.outSuccessJson(response);
	}

	/**
	 * 根据userId查询分配的角色
	 *  @param userId
	 **/
	@RequestMapping("/findUserRoleList")
	@ResponseBody
	public void findUserRoleList(HttpServletRequest request,HttpServletResponse response){
		Long userId = getLong("userId");
		if(null != userId){
			List<AuthUserRole> aupList = authUserRoleService.findByUserId(userId);
			outJson(response, aupList);
		}
	}

	/**
	 * 保存用户分配的角色
	 * @param userId 用户Id
	 * @param roleIds 分配角色Id集合
	 **/
	@RequestMapping("/saveUserRole")
	@ResponseBody
	public  void  saveUserRole(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//删除非内定角色
		Long userId = getLong("userId");
		if(null != userId){
			List<AuthUserRole> list = authUserRoleService.findBy("userId",userId);
			for(AuthUserRole obj : list){
				authUserRoleService.deleteBy("userId", obj.getUserId(), "roleId", obj.getRoleId());
			}
		}
		String roleIds = get("roleIds");
		if(StringUtils.isNotBlank(roleIds)){
			List<Long> roleList = CollectionUtils.splitAsLong(roleIds);
			for(Long roleId : roleList){
				AuthUserRole authUserRole = new AuthUserRole();
				authUserRole.setUserId(userId);
				authUserRole.setRoleId(roleId);
				authUserRoleService.save(authUserRole);
			}
		}
		logService.add(request, "添加分配角色操作");
		this.outSuccessJson(response);
	}


	/**
	 * 根据userId查询分配的功能
	 *  @param userId
	 **/
	@RequestMapping("/findUserPermissionList")
	@ResponseBody
	public void findUserPermissionList(HttpServletRequest request, HttpServletResponse response){
		Long userId = getLong("userId");
		if(null != userId){
			List<AuthUserPermission> aupList = authUserPermissionService.findByUserId(userId);
			outJson(response, aupList);
		}
	}

	/**
	 * 保存用户分配的功能权限
	 * @param userId 用户Id
	 * @param permissionIds 分配功能Id集合
	 **/
	@RequestMapping("/saveUserPermission")
	@ResponseBody
	public  void  saveUserPermission(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long userId = getLong("userId");
		if(null != userId){
			authUserPermissionService.deleteByUserId(userId);
		}
		String permissionIds = get("permissionIds");
		if(StringUtils.isNotBlank(permissionIds)){
			permissionIds = permissionIds.replaceAll("null,", "");
			String [] ids = permissionIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				AuthUserPermission authUserPermission = new AuthUserPermission();
				authUserPermission.setUserId(userId);
				authUserPermission.setPermissionId(Long.parseLong(ids[i]));
				authUserPermissionService.save(authUserPermission);
			}
		}
		logService.add(request, "添加了用户功能权限操作");
		this.outSuccessJson(response);
	}

	/**
	 * 根据userId查询分配的组
	 *  @param userId
	 **/
	@RequestMapping("/findUserGroupList")
	@ResponseBody
	public void findUserGroupList(HttpServletRequest request, HttpServletResponse response){
		Long userId = getLong("userId");
		if(null != userId){
			List<AuthUserGroup> aupList = authUserGroupService.findByUserId(userId);
			outJson(response, aupList);
		}
	}

	/**
	 * 查找除了userId以外的所有user列表
	 * @return
	 */
	@RequestMapping("/findExceptByUserId")
	@ResponseBody
	public void findExceptByUserId(HttpServletRequest request, HttpServletResponse response){
		Long userId = getLong("userId");
		if(null != userId){
			List<AuthUser> aupList = authUserService.findExceptByUserId(userId);
			outJson(response, aupList);
		}
	}



	/**
	 * 保存用户分配的组
	 * @param userId 用户Id
	 * @param groupIds 分配组Id集合
	 **/
	@RequestMapping("/saveUserGroup")
	@ResponseBody
	public  void  saveUserGroup(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long userId = getLong("userId");
		if(null != userId){
			authUserGroupService.deleteByUserId(userId);
		}
		String groupIds = get("groupIds");
		if(StringUtils.isNotBlank(groupIds)){
			String [] ids = groupIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				AuthUserGroup authUserGroup = new AuthUserGroup();
				authUserGroup.setUserId(userId);
				authUserGroup.setGroupId(Long.parseLong(ids[i]));
				authUserGroupService.save(authUserGroup);
			}
		}
		logService.add(request, "添加用户分配组操作");
		this.outSuccessJson(response);
	}

	/**
	 * 根据userId查找用户关系列表
	 * @param userId 用户Id
	 **/
	@RequestMapping("/findUserRelationList")
	@ResponseBody
	public  void  findUserRelationList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long userId = getLong("userId");
		ArrayList<AuthUserRelation> arpList = new ArrayList<AuthUserRelation>();
		if(null != userId){
			arpList = (ArrayList<AuthUserRelation>) authUserRelationService.findUserRelationByUserId(userId);
		}
		outJson(response, arpList);
	}


	/**
	 * 保存用户关系
	 * @param userId 用户Id
	 * @param userIds 添加用户关系Id集合
	 **/
	@RequestMapping("/saveUserRelation")
	@ResponseBody
	public  void  saveUserRelation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long userId = getLong("userId");
		if(null != userId){
			authUserRelationService.deleteByUserId(userId);
		}
		Set<Long> authUserRelationSet = new HashSet<Long>();
		List<AuthUserRelation> userRelationList = authUserRelationService.findAll();
		String userIds = get("parentUserIds");
		//添加关系前将本身添加到关系中
		AuthUserRelation authUserRelation = new AuthUserRelation();
		authUserRelation.setUserId(userId);
		authUserRelation.setParentUserId(userId);
		authUserRelationService.save(authUserRelation);
		if(StringUtils.isNotBlank(userIds)){
			String [] ids = userIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				//根据id查找与当前id有关系的id列表
				List<AuthUserRelation> authUserRelationList = authUserRelationService.findUserRelationByUserId(Long.parseLong(ids[i]));
				if(null != authUserRelationList && authUserRelationList.size()>0){
					for (AuthUserRelation userRel:authUserRelationList) {
						AuthUserRelation aur = new AuthUserRelation();
						aur.setUserId(userId);
						aur.setParentUserId(userRel.getParentUserId());
						authUserRelationService.save(aur);
					}
				}else{
					authUserRelation = new AuthUserRelation();
					authUserRelation.setUserId(userId);
					authUserRelation.setParentUserId(Long.parseLong(ids[i]));
					authUserRelationService.save(authUserRelation);
				}
			}
		}

		if(null != userRelationList && userRelationList.size()>0){
			for (AuthUserRelation userRelation:userRelationList) {
				authUserRelationSet.add(userRelation.getParentUserId());
			}
			if (authUserRelationSet.contains(authUserRelation.getUserId())){
				authUserRelationService.deleteByUserId(userId);
				return;
			}
		}
		this.outSuccessJson(response);
		logService.add(request, "添加用户关系操作");
		this.outSuccessJson(response);
	}

    /**
     * 验证导入用户是否可用
     *
     * @param authUser      待验证用户
     * @param i             excel行数
     * @param errorMessages 错误数据集合
     */
    private boolean verifyUserAvailable(AuthUser authUser, int i, HashSet<String> errorMessages) {
        AuthUser validatorEmail = authUserService.getUserByLoginNameOrEmailOrMobile("principal", authUser.getEmail());
        if(validatorEmail != null){
            errorMessages.add("第" + i + "行，邮箱[" + authUser.getEmail() + "]已经被使用。");
            return false;
        }
        AuthUser validatorName = authUserService.getUserByLoginNameOrEmailOrMobile("principal", authUser.getName());
        if(validatorName != null){
            errorMessages.add("第" + i + "行，登陆名[" + authUser.getName() + "]已经被使用。");
            return false;
        }
        AuthUser validatorMobile = authUserService.getUserByLoginNameOrEmailOrMobile("principal", authUser.getMobile());
        if(validatorMobile != null){
            errorMessages.add("第" + i + "行，手机[" + authUser.getMobile() + "]已经被使用。");
            return false;
        }
        return true;
    }
}

