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
import com.xwd.log.model.LogAccount;
import com.xwd.log.model.LogBuyerCommission;
import com.xwd.log.service.LogAccountService;

@Controller
@Scope("prototype")
@RequestMapping("/admin/base/logAccount")
public class LogAccountController extends BaseAdminController {
	
	@Autowired
	private LogAccountService logAccountService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/log/logAccount";
	}
	
	/** 
	 * 查询列表
	 * 
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		
		/*String customerNo =  (String) pageRequest.getFilters().get("customerNo");
		if(!"".equals(customerNo)&&customerNo!="null"){
			pageRequest.getFilters().put("customerNo", customerNo);
			Page<LogAccount> page = logAccountService.findByPageRequest(pageRequest);
			this.outPageJson(response, page, true);
		}*/
		
		Page<LogAccount> page = logAccountService.findByPageRequest(pageRequest);
		this.outPageJson(response, page, true);
	}
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		AuthUser user = (AuthUser) SecurityUtils.getUser();
		LogAccount  entity;
		Long id = this.getLong("id");
		if(id == null){
			entity = new LogAccount();
			this.setFieldValues(entity, request, false);
		}else{
			entity = logAccountService.get(id);
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			this.setFieldValues(entity, request, true);
		}
		logAccountService.saveOrUpdate(entity);
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
		logAccountService.deleteByIds(ids);
		outSuccessJson(response);
	}
	
}

