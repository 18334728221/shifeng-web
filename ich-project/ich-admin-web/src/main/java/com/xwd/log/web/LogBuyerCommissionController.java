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
import com.xwd.log.model.LogBuyerCommission;
import com.xwd.log.service.LogBuyerCommissionService;

@Controller
@Scope("prototype")
@RequestMapping("/admin/base/logBuyerCommission")
public class LogBuyerCommissionController extends BaseAdminController {
	
	@Autowired
	private LogBuyerCommissionService logBuyerCommissionService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/log/logBuyerCommission";
	}
	
	/** 
	 * 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		/*String customerNo = String.valueOf(pageRequest.getFilters().get("customerNo"));
		if(!"".equals(customerNo)&&customerNo!="null"){
			Page<LogBuyerCommission> page = logBuyerCommissionService.findByPageRequest(pageRequest);
			this.outPageJson(response, page, true);
		}*/
		Page<LogBuyerCommission> page = logBuyerCommissionService.findByPageRequest(pageRequest);
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
		LogBuyerCommission  entity;
		Long id = this.getLong("id");
		if(id == null){
			entity = new LogBuyerCommission();
			this.setFieldValues(entity, request, false);
		}else{
			entity = logBuyerCommissionService.get(id);
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			this.setFieldValues(entity, request, true);
		}
		logBuyerCommissionService.saveOrUpdate(entity);
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
		logBuyerCommissionService.deleteByIds(ids);
		outSuccessJson(response);
	}
	
}

