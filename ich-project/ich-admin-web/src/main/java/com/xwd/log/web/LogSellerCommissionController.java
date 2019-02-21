package com.xwd.log.web;
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
import com.xwd.base.web.BaseAdminController;
import com.xwd.log.model.LogSellerCommission;
import com.xwd.log.service.LogSellerCommissionService;

@Controller
@Scope("prototype")
@RequestMapping("/admin/base/logSellerCommission")
public class LogSellerCommissionController extends BaseAdminController {
	
	@Autowired
	private LogSellerCommissionService logSellerCommissionService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/log/logSellerCommission";
	}
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		/*String customerNo = String.valueOf(mapFilters.get("customerNo"));
		if(!"".equals(customerNo)&&customerNo!="null"){
			Page<LogSellerCommission> page = logSellerCommissionService.findByPageRequest(pageRequest);
			this.outPageJson(response, page, true);
		}*/
		Page<LogSellerCommission> page = logSellerCommissionService.findByPageRequest(pageRequest);
		this.outPageJson(response, page, true);
		outSuccessJson(response);
	}
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		AuthUser user = (AuthUser) SecurityUtils.getUser();
		LogSellerCommission  entity;
		Long id = this.getLong("id");
		if(id == null){
			entity = new LogSellerCommission();
			this.setFieldValues(entity, request, false);
		}else{
			entity = logSellerCommissionService.get(id);
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			this.setFieldValues(entity, request, true);
		}
		logSellerCommissionService.saveOrUpdate(entity);
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
		logSellerCommissionService.deleteByIds(ids);
		outSuccessJson(response);
	}
	
}

