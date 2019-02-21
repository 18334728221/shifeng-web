package com.xwd.product.action;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.base.web.BaseAdminController;
import com.xwd.log.service.LogService;
import com.xwd.product.service.ProductService;

@Controller
@Scope("prototype")
@RequestMapping("/public/product/product")
public class ProductPublic extends BaseAdminController {
	
	@Autowired
	private LogService logService;
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/base/product";
	}
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		Page page = productService.findByPageRequest(pageRequest);
		this.outPageJson(response, page, true);
	}
	
	
}

