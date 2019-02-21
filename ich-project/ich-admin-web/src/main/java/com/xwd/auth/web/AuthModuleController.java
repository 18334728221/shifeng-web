package com.xwd.auth.web;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import com.xwd.auth.service.AuthComprisedService;
import com.xwd.auth.service.AuthModuleService;
import com.xwd.base.constant.RedisBaseConstant;
import com.xwd.base.web.BaseAdminController;

/**
 * 模块
 * @author ljl
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin/auth/module")
public class AuthModuleController extends BaseAdminController {
	
	@Autowired
	private AuthModuleService authModuleService;
	@Autowired
	private AuthComprisedService authComprisedService;
	@Autowired 
	private RedisTemplate<String, String> redisTemplate;
	
	@RequestMapping("/index")
	public String index(){
		return "admin/auth/module";
	}
	
	/** 
	 * 执行搜索 .
	 **/
	@RequestMapping("findAll")
	@ResponseBody
	public	void findAll(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue( request); 
		Page<AuthModule> page = authModuleService.findByPageRequest(pageRequest);
		List<AuthModule> list = page.getResult();
		for(AuthModule obj : list){
			obj.setControlLevelName(RoleConstant.ROLE_CONTROL_LEVEL_MAP.get(obj.getControlLevel() + ""));
		}
		this.outPageJson(response, page, false);
	}
	
	
	/** 
	 * 保存对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		AuthModule authModule = null;
		Long id = getLong("id"); 
		if( id == null){ 
			authModule = new AuthModule(); 
		    this.setFieldValues(authModule, request, false); 
		    authModuleService.save(authModule); 
		}else{ 
			authModule = authModuleService.get(id); 
		    if(authModule != null){ 
		    	this.setFieldValues(authModule, request, true); 
		    	authModuleService.update(authModule); 
		    } 
		}
		redisTemplate.delete(RedisBaseConstant.REDIS_MODULE_PERMISSION_TREE_JSON_KEY);
	}
	
	
	/**
	 * 删除对象.
	 **/
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response){
		String ids = get("ids");
		if(StringUtils.isNotBlank(ids)){
			authModuleService.deleteByIds(ids);
		}
		redisTemplate.delete(RedisBaseConstant.REDIS_MODULE_PERMISSION_TREE_JSON_KEY);
	}
	/**
	 * 查询模块列表
	 * @param request
	 * @param response
	 * @param PermissionList （为空查找模块列表，不为空查找模块和功能列表）
	 * @throws Exception
	 */
	@RequestMapping("/findListAllTree")
    @ResponseBody
	public  void findListAllTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//redisTemplate.delete(RedisConstant.REDIS_MODULE_PERMISSION_TREE_JSON_KEY);
		String str = redisTemplate.boundValueOps(RedisBaseConstant.REDIS_MODULE_PERMISSION_TREE_JSON_KEY).get();
		if (str == null) {
			str = authComprisedService.findModulePermissionAsJson();
			if(!str.equals("[null]")){
				redisTemplate.boundValueOps(RedisBaseConstant.REDIS_MODULE_PERMISSION_TREE_JSON_KEY).set(str);
				redisTemplate.expire(RedisBaseConstant.REDIS_MODULE_PERMISSION_TREE_JSON_KEY, 1, TimeUnit.DAYS);
			}
		}
		this.outJson(response, str);
	}
	
	/**
	 * 查询模块列表
	 * @param request
	 * @param response
	 * @param PermissionList （为空查找模块列表，不为空查找模块和功能列表）
	 * @throws Exception
	 */
	@RequestMapping("/findListAllModuleTree")
    @ResponseBody
	public  void findListAllModuleTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//redisTemplate.delete(RedisConstant.REDIS_MODULE_PERMISSION_TREE_JSON_KEY);
		String str = redisTemplate.boundValueOps(RedisBaseConstant.REDIS_MODULE_PERMISSION_TREE_JSON_KEY).get();
		if (str == null) {
			str = authComprisedService.findModuleAsJson();
			if(str!=null && !str.equals("[null]")){
				redisTemplate.boundValueOps(RedisBaseConstant.REDIS_MODULE_PERMISSION_TREE_JSON_KEY).set(str);
				redisTemplate.expire(RedisBaseConstant.REDIS_MODULE_PERMISSION_TREE_JSON_KEY, 1, TimeUnit.DAYS);
			}
		}
		this.outJson(response, str);
	}
}

