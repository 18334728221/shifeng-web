package com.xwd.trade.web;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.auth.SecurityUtils;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.frame.util.CalendarUtils;
import com.xwd.auth.model.AuthUser;
import com.xwd.base.model.Festival;
import com.xwd.base.service.FestivalService;
import com.xwd.base.web.BaseAdminController;

@Controller
@Scope("prototype")
@RequestMapping("/admin/base/festival")
public class FestivalController extends BaseAdminController {
	
	@Autowired
	private FestivalService festivalService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/base/festival";
	}
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		Page<Festival> page = festivalService.findByPageRequest(pageRequest);
		this.outPageJson(response, page, true);
	}
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		Festival  entity;
		Long id = this.getLong("id");
		if(id == null){
			entity = new Festival();
			this.setFieldValues(entity, request, false);
		}else{
			entity = festivalService.get(id);
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			this.setFieldValues(entity, request, true);
		}
		festivalService.saveOrUpdate(entity);
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
		festivalService.deleteByIds(ids);
		outSuccessJson(response);
	}
	
}

