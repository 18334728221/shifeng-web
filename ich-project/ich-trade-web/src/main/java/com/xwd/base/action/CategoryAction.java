package com.xwd.base.action;
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
import com.xwd.base.service.CategoryService;
import com.xwd.base.web.BaseAdminController;
import com.xwd.customer.model.Customer;
import com.xwd.log.service.LogService;

@Controller
@Scope("prototype")
@RequestMapping("/action/base/category")
public class CategoryAction extends BaseAdminController {
	
	@Autowired
	private LogService logService;
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/base/category";
	}
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Customer customer = (Customer) SecurityUtils.getUser();
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		Page page = categoryService.findByPageRequest(pageRequest);
		this.outPageJson(response, page, true);
	}
	
}

