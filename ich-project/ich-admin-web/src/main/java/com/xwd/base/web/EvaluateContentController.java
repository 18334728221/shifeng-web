package com.xwd.base.web;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth.SecurityUtils;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthUser;
import com.xwd.base.model.EvaluateContent;
import com.xwd.base.service.EvaluateContentService;
import com.xwd.log.service.LogService;

@Controller
@Scope("prototype")
@RequestMapping("/admin/base/evaluateContent")
public class EvaluateContentController extends BaseAdminController {
	
	@Autowired
	private EvaluateContentService evaluateContentService;
	@Autowired
	private LogService logService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/base/evaluateContent";
	}
	
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		Page<EvaluateContent> page = evaluateContentService.findByPageRequest(pageRequest);
		logService.add(request, "查询评价内容");
		this.outPageJson(response, page, true);
	}
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		AuthUser user = (AuthUser) SecurityUtils.getUser();
		EvaluateContent  entity;
		Long id = this.getLong("id");
		if(id == null){
			entity = new EvaluateContent();
			logService.add(request, "新增评价内容");
			this.setFieldValues(entity, request, false);
		}else{
			entity = evaluateContentService.get(id);
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			logService.add(request, "修改评价内容");
			this.setFieldValues(entity, request, true);
		}
		evaluateContentService.saveOrUpdate(entity);
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
		evaluateContentService.deleteByIds(ids);
		logService.add(request, "删除评价内容");
		this.outSuccessJson(response);
	}
	
}

