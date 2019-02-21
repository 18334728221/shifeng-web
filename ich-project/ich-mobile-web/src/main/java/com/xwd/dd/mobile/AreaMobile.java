package com.xwd.dd.mobile;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth.SecurityUtils;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthUser;
import com.xwd.base.constant.RedisBaseConstant;
import com.xwd.base.web.BaseAdminController;
import com.xwd.dd.model.Area;
import com.xwd.dd.service.AreaService;
import com.xwd.log.service.LogService;

@Controller
@Scope("prototype")
@RequestMapping("/mobile/dd/area")
public class AreaMobile extends BaseAdminController {
	
	@Autowired
	private AreaService areaService;
	@Autowired
	private LogService logService;
	@Autowired 
	private RedisTemplate<String, String> redisTemplate;
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/findAll")
	@ResponseBody
	public void findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Page<Area> page = areaService.findByPageRequest(pageRequest);
		this.outPageJson(response, page, true);
	}
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		AuthUser user = (AuthUser) SecurityUtils.getUser();
		Area entity;
		Long id = this.getLong("id");
		if(id == null){
			entity = new Area();
			this.setFieldValues(entity, request, false);
			logService.add(request, "");
		}else{
			entity = areaService.get(id);
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			this.setFieldValues(entity, request, true);
			logService.add(request, "");
		}
		areaService.saveOrUpdate(entity);
		this.outSuccessJson(response);
	}
	
	/**
	 * 删除对象.
	 * 这里接受一个名称为“ids”的字符串，id之间用英文半角的逗号“,”分隔。
	 **/
	@RequestMapping("/delete")
	@ResponseBody
	public 	void delete(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String ids = get("ids");
		areaService.deleteByIds(ids);
		logService.add(request, "");
		this.outSuccessJson(response);
	}
	
	@RequestMapping("/findAllTree")
	@ResponseBody
	public 	void findAllTree(HttpServletRequest request, HttpServletResponse response)throws Exception {
		redisTemplate.delete(RedisBaseConstant.REDIS_AREA_TREE_JSON_KEY);
		String str = redisTemplate.boundValueOps(RedisBaseConstant.REDIS_AREA_TREE_JSON_KEY).get();
		if (str == null) {
			str = areaService.listAllTree();
			if(str!=null && !str.equals("[null]")){
				redisTemplate.boundValueOps(RedisBaseConstant.REDIS_AREA_TREE_JSON_KEY).set(str);
				redisTemplate.expire(RedisBaseConstant.REDIS_AREA_TREE_JSON_KEY, 1, TimeUnit.DAYS);
			}
		}
		this.outJson(response, str);
	}
}