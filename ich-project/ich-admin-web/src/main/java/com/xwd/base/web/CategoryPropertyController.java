package com.xwd.base.web;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth.SecurityUtils;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthUser;
import com.xwd.base.model.CategoryProperty;
import com.xwd.base.provider.CategoryPropertyProvider;
import com.xwd.base.service.CategoryPropertyService;
import com.xwd.log.service.LogService;

/**
 * 产品属性
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin/base/categoryProperty")
public class CategoryPropertyController extends BaseAdminController {
	
	@Autowired
	private CategoryPropertyService categoryPropertyService;
	
	@Autowired
	private CategoryPropertyProvider categoryPropertyProvider;
	
	@Autowired
	private LogService logService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/base/categoryProperty";
	}
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		Page<CategoryProperty> page = categoryPropertyService.findByPageRequest(pageRequest);
		logService.add(request, "分页查询产品属性信息。");
		this.outPageJson(response, page, true);
	}
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		CategoryProperty  entity;
		Long id = this.getLong("id");
		if(id == null){
			logService.add(request, "新增产品属性信息。");
			entity = new CategoryProperty();
			this.setFieldValues(entity, request, false);
		}else{
			logService.add(request, "修改产品属性信息。");
			entity = categoryPropertyService.get(id);
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			this.setFieldValues(entity, request, true);
		}
		categoryPropertyService.saveOrUpdate(entity);
		if(id==null){
			categoryPropertyProvider.save(entity);
		}else{
			categoryPropertyProvider.update(entity);
		}
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
		categoryPropertyService.deleteByIds(ids);
		logService.add(request, "删除产品属性信息。");
		if(StringUtils.isNotBlank(ids)){
			String [] id = ids.split(",");
			for (String string : id) {
				//CategoryProperty categoryproperty = categoryPropertyService.get();
				categoryPropertyProvider.delete(Long.valueOf(string));
			}
		}
		this.outSuccessJson(response);
	}
	
}

