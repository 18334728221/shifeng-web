package com.xwd.auth.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth.constant.RoleConstant;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthModule;
import com.xwd.auth.model.AuthPermission;
import com.xwd.auth.service.AuthModuleService;
import com.xwd.auth.service.AuthPermissionService;
import com.xwd.base.constant.RedisBaseConstant;
import com.xwd.base.web.BaseAdminController;
import com.xwd.bean.SelectModel;



/**
 * 权限码
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin/auth/permission")
public class AuthPermissionController extends BaseAdminController {
 
	@Autowired
	private AuthPermissionService authPermissionService;
	@Autowired
	private AuthModuleService authModuleService;
	@Autowired 
	private RedisTemplate<String, String> redisTemplate;
	
	
	@RequestMapping("/index")
	public String index(){
		return "admin/auth/function";
	}
	
	/** 
	 * 执行搜索 .
	 **/
	@RequestMapping("findAll")
	@ResponseBody
	public	void findAll(HttpServletRequest request, HttpServletResponse response)throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue( request); 
		Page<AuthPermission> page = authPermissionService.findByPageRequest(pageRequest);
		List<AuthPermission> list = page.getResult();
		for(AuthPermission obj : list){
			obj.setControlLevelName(RoleConstant.ROLE_CONTROL_LEVEL_MAP.get(obj.getControlLevel()+""));
		}
		this.outPageJson(response, page, false);
	}
	
	
	/** 
	 * 保存对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		AuthPermission authPermission = null;
		Long id = getLong("id"); 
		if( id == null){ 
			authPermission = new AuthPermission(); 
		    this.setFieldValues(authPermission, request, false); 
		    authPermissionService.save(authPermission);
		}else{ 
			authPermission = authPermissionService.get(id); 
		    if(authPermission != null){ 
		    	this.setFieldValues(authPermission, request, true); 
		    	authPermissionService.update(authPermission); 
		    } 
		}
		redisTemplate.delete(RedisBaseConstant.REDIS_MODULE_PERMISSION_TREE_JSON_KEY);
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
			authPermissionService.deleteByIds(ids);
		}
		redisTemplate.delete(RedisBaseConstant.REDIS_MODULE_PERMISSION_TREE_JSON_KEY);
		this.outSuccessJson(response);
	}

	@RequestMapping("/left")
	public String left(){
		return "admin/auth/function";
	}
	
	@RequestMapping("/edit")
	public String edit(HttpServletRequest request, HttpServletResponse response){
		Long id = getLong("id");
		if(id != null){
			AuthPermission authPermission = authPermissionService.get(id);
			request.setAttribute("authPermission", authPermission);
		}
		
		return "admin/auth/editFunction";
	}
	
	/**
	 * 查找模块
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/findModule")
	@ResponseBody 
	public void findModule(HttpServletRequest request,HttpServletResponse response) throws Exception {
		List<AuthModule> list = authModuleService.findAll();
		List<SelectModel> cdmList = new ArrayList<SelectModel>();
		for (int i = 0; i < list.size(); i++) {
			SelectModel cdModel = new SelectModel();
			AuthModule authModule = list.get(i);
			cdModel.setText(authModule.getModuleName());
			cdModel.setValue(authModule.getId().toString());
			cdmList.add(cdModel);
		}
		this.outJson(response, cdmList);
	}
}

