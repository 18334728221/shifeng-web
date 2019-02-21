package com.xwd.base.mobile;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xwd.base.service.CategoryPropertyService;
import com.xwd.base.web.BaseAdminController;
import com.xwd.log.service.LogService;
import com.xwd.product.model.Product;
import com.xwd.product.service.ProductService;


/**
 * 产品属性
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/base/property")
public class CategoryPropertyMobile extends BaseAdminController {
	
	@Autowired
	private LogService logService;
	@Autowired
	private CategoryPropertyService categoryPropertyService;
	@Autowired
	private ProductService productService;
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String productCode = get("productCode");
		if(StringUtils.isBlank(productCode)){
			outFailureJson(response,"传入参数有误");
			return;
		}
		Product product = productService.findUniqueBy("code",productCode);
		
		Map<String, Map<Long, String>> categoryProperty = categoryPropertyService.findCategoryProperty(product.getCategoryId());
		logService.add(request, "获取产品属性");
		outJson(response,categoryProperty );
	}
	
	
	
}

