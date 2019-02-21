package com.xwd.auth.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth.SecurityUtils;
import com.auth.constant.RoleConstant;
import com.auth.provider.ForwardingAuthRealm;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthRole;
import com.xwd.auth.model.AuthRolePermission;
import com.xwd.auth.service.AuthRolePermissionService;
import com.xwd.auth.service.AuthRoleService;
import com.xwd.base.web.BaseAdminController;
import com.xwd.bean.SelectModel;

/**
 * 角色
 * @author ljl
 *
 */
@Controller
@Scope("prototype")
@SuppressWarnings("rawtypes")
@RequestMapping("/admin/auth/role")
public class AuthRoleController extends BaseAdminController {
	
	@Autowired
	private AuthRolePermissionService authRolePermissionService;
	@Autowired
	private AuthRoleService authRoleService;
	@Autowired
	private ForwardingAuthRealm realm;
	
	@RequestMapping("/index")
	public String index(){
		return "admin/auth/role";
	}
	
	/** 
	 * 执行搜索 .
	 **/
	@RequestMapping("/findAll")
	@ResponseBody
	public void findAll(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);   
		Map mapFilters = pageRequest.getFilters();
		//超级管理员拥有所有的权限
		if(!SecurityUtils.getAuthorizationPrincipal().hasRole(RoleConstant.ROLE_SUPERADMIN)){
			mapFilters.put("isInnerRole", 0);
		}
		Page<AuthRole> page = authRoleService.findByPageRequest(pageRequest);
		this.outPageJson(response, page, true);
	}
	
	
	/** 
	 * 保存对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		AuthRole authRole = null;
		Long id = getLong("id"); 
		if( id == null){ 
			authRole = new AuthRole(); 
		    this.setFieldValues(authRole, request, false); 
		    authRoleService.save(authRole); 
		}else{ 
			authRole = authRoleService.get(id); 
		    if(authRole != null){ 
		    	this.setFieldValues(authRole, request, true); 
		    	authRoleService.update(authRole); 
		    } 
		    realm.updateAuthModifyTime();
		    
		}
		this.outSuccessJson(response);
	}
	
	/**
	 * 删除对象.
	 **/
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ids = get("ids");
		if(StringUtils.isNotBlank(ids)){
			authRoleService.deleteByIds(ids);
		}
		realm.updateAuthModifyTime();
		this.outSuccessJson(response);
	}
	
	/** 
	 * 根据roleId查询分配的功能
	 *  @param userId
	 **/
	@RequestMapping("/findRolePermissionList")
	@ResponseBody
	public void findRolePermissionList(HttpServletRequest request, HttpServletResponse response){
		Long roleId = getLong("roleId");
		if(null != roleId){
			List<AuthRolePermission> arpList = authRolePermissionService.findByRoleId(roleId);
			outJson(response, arpList);
		}
	}
	
	/** 
	 * 保存用户分配的角色
	 * @param roleId 角色Id
	 * @param permissionIds 分配功能Id集合
	 **/
	@RequestMapping("/saveRolePermission")
	@ResponseBody
	public  void  saveRolePermission(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long roleId = getLong("roleId");
		if(null != roleId){
			authRolePermissionService.deleteByRoleId(roleId);
		}
		String permissionIds = get("permissionIds");
		permissionIds = permissionIds.replaceAll("null,", "");
		if(StringUtils.isNotBlank(permissionIds)){
			String [] ids = permissionIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				AuthRolePermission authRolePermission = new AuthRolePermission();
				authRolePermission.setRoleId(roleId);
				authRolePermission.setPermissionId(Long.parseLong(ids[i]));
				authRolePermissionService.save(authRolePermission);
			}
		}
		this.outSuccessJson(response);
	}
	
	
	/**
	 * 查找用户类型
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/findUserType")	
	@ResponseBody 
	public void findUserType(HttpServletRequest request,HttpServletResponse response) throws Exception {
		List<AuthRole> list = authRoleService.findBy("isUserType", true);
		List<SelectModel> cdmList = new ArrayList<SelectModel>();
		for (int i = 0; i < list.size(); i++) {
			SelectModel cdModel = new SelectModel();
			AuthRole authRole = list.get(i);
			cdModel.setText(authRole.getDescription());
			cdModel.setValue(authRole.getId().toString());
			cdmList.add(cdModel);
		}
		this.outJson(response, cdmList);
	}
	
	/**
	 * 获得控制级别角色
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/findControlLevelRole")	
	@ResponseBody 
	public void findControlLevelRole(HttpServletRequest request,HttpServletResponse response) throws Exception {
		List<SelectModel> cdmList = new ArrayList<SelectModel>();
		for(Entry<String,String> entry :  RoleConstant.ROLE_CONTROL_LEVEL_MAP.entrySet()){
			SelectModel cdModel = new SelectModel();
			cdModel.setText(entry.getValue());
			cdModel.setValue(entry.getKey());
			cdmList.add(cdModel);
		}
		this.outJson(response, cdmList);
	}
	 
}

