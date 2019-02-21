package com.xwd.auth.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth.AuthRealm;
import com.auth.SecurityUtils;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthChildrenGroup;
import com.xwd.auth.model.AuthGroup;
import com.xwd.auth.model.AuthGroupPermission;
import com.xwd.auth.model.AuthGroupRole;
import com.xwd.auth.service.AuthChildrenGroupService;
import com.xwd.auth.service.AuthGroupPermissionService;
import com.xwd.auth.service.AuthGroupRoleService;
import com.xwd.auth.service.AuthGroupService;
import com.xwd.base.constant.MessageConstant;
import com.xwd.base.web.BaseAdminController;
import com.xwd.log.service.LogService;


/**
 * 权限组相关操作web层
 * @author ljl
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin/auth/group")
public class AuthGroupController extends BaseAdminController {
	
	@Autowired
	private AuthGroupService authGroupService;
	@Autowired
	private AuthGroupRoleService authGroupRoleService;
	@Autowired
	private AuthGroupPermissionService authGroupPermissionService;
	@Autowired
	private AuthChildrenGroupService authChildrenGroupService;
	@Autowired
	private AuthRealm<Long> delegate;
	@Autowired
	private LogService logService;
	
	@RequestMapping("/index")
	public String index(){
		return "admin/auth/group";
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public void findAll(HttpServletRequest request, HttpServletResponse response)throws Exception {
		SecurityUtils.getUser();
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);   
		Page<AuthGroup> page = authGroupService.findByPageRequest(pageRequest);
		this.outPageJson(response, page, false);
	}
	
	
	/** 
	 * 保存对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		AuthGroup authGroup = new AuthGroup();
		this.setFieldValues(authGroup, request, false);
		authGroupService.saveOrUpdate(authGroup);
		logService.add(request, "添加了组操作");
		this.outSuccessJson(response);
	}
	
	
	/**
	 * 删除对象.
	 **/
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String ids = get("ids");
		if(StringUtils.isNotBlank(ids)){
			authGroupService.deleteByIds(ids);
		}
		logService.add(request, "删除了组操作");
		this.outSuccessJson(response);
	}
	
	/** 
	 * 根据groupId查询分配的角色
	 *  @param groupId
	 **/
	@RequestMapping("/findGroupRoleList")
	@ResponseBody
	public void findGroupRoleList(HttpServletRequest request, HttpServletResponse response){
		Long groupId = getLong("groupId");
		if(null != groupId){
			List<AuthGroupRole> arpList = authGroupRoleService.findBy("groupId", groupId);
			outJson(response, arpList);
		}
	}
	
	
	/** 
	 * 保存组分配的角色
	 * @param groupId 组Id
	 * @param roleIds 分配角色Id集合
	 **/
	@RequestMapping("/saveGroupRole")
	@ResponseBody
	public  void  saveGroupRole(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String roleIds = get("roleIds");
		if(StringUtils.isNotBlank(roleIds)){
			String [] ids = roleIds.split(",");
			Long groupId = getLong("groupId");
			if(null != groupId){
				authGroupRoleService.deleteBy("groupId", groupId);
			}
			for (int i = 0; i < ids.length; i++) {
				AuthGroupRole authGroupRole = new AuthGroupRole();
				authGroupRole.setGroupId(groupId);
				authGroupRole.setRoleId(Long.parseLong(ids[i]));
				authGroupRoleService.save(authGroupRole);
			}
		}
		logService.add(request, "保存组分配的角色操作");
		this.outSuccessJson(response);
	}
	
	/** 
	 * 根据groupId查询分配的功能
	 *  @param groupId
	 **/
	@RequestMapping("/findGroupPermissionList")
	@ResponseBody
	public void findGroupPermissionList(HttpServletRequest request, HttpServletResponse response){
		Long groupId = getLong("groupId");
		if(null != groupId){
			List<AuthGroupPermission> arpList = authGroupPermissionService.findByGroupId(groupId);
			outJson(response, arpList);
		}
	}
	
	/** 
	 * 保存组分配的功能
	 * @param groupId 组Id
	 * @param permissionIds 分配功能Id集合
	 **/
	@RequestMapping("/saveGroupPermission")
	@ResponseBody
	public  void  saveGroupPermission(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long groupId = getLong("groupId");
		if(null != groupId){
			authGroupPermissionService.deleteByGroupId(groupId);
		}
		String permissionIds = get("permissionIds");
		if(StringUtils.isNotBlank(permissionIds)){
			permissionIds = permissionIds.replaceAll("null,", "");
			String [] ids = permissionIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				AuthGroupPermission authGroupPermission = new AuthGroupPermission();
				authGroupPermission.setGroupId(groupId);
				authGroupPermission.setPermissionId(Long.parseLong(ids[i]));
				authGroupPermissionService.save(authGroupPermission);
			}
		}
		this.outSuccessJson(response);
	}
	
	/** 
	 * 查找出groupId以外的所有组列表
	 * @param groupId 组Id
	 **/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/findExceptByGroupId")
	@ResponseBody
	public  void  findExceptByGroupId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);   
		Map mapFilters = pageRequest.getFilters();
		Long groupId = getLong("groupId");
		mapFilters.put("id", groupId);
		Page page = this.authGroupService.findExceptByGroupIdPageRequest(pageRequest);
		this.outPageJson(response, page, false);
	}
	
	/** 
	 * 根据groupId查询分配的组
	 *  @param groupId
	 **/
	@RequestMapping("/findChildrenGroupList")
	@ResponseBody
	public void findChildrenGroupList(HttpServletRequest request, HttpServletResponse response){
		Long groupId = getLong("groupId");
		if(null != groupId){
			List<AuthGroupPermission> arpList = authChildrenGroupService.findByGroupId(groupId);
			outJson(response, arpList);
		}
	}
	
	@RequestMapping("/saveGroupChildrenGroup")
	@ResponseBody
	public void saveGroupChildrenGroup(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long groupId = getLong("groupId");
		if(null != groupId){
			authChildrenGroupService.deleteByGroupId(groupId);
		}
		String groupIds = get("groupIds");
		if(StringUtils.isNotBlank(groupIds)){
			groupIds = groupIds.replaceAll("null,", "");
			String [] ids = groupIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				AuthChildrenGroup authChildrenGroup = new AuthChildrenGroup();
				authChildrenGroup.setGroupId(groupId);
				authChildrenGroup.setChildrenGroupId(Long.parseLong(ids[i]));
				authChildrenGroupService.save(authChildrenGroup);
			}
		}
		
		try {
			delegate.checkClosedCycle(true);
			this.outSuccessJson(response);
		} catch (Exception e) {
			authChildrenGroupService.deleteByGroupId(groupId);
			this.outResultJsoni18n(response, MessageConstant.GROUP_SHARE_INFO);
			return;
		}
	}
	
}

