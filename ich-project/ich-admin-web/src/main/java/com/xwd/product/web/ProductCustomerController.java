package com.xwd.product.web;
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
import com.xwd.log.service.LogService;
import com.xwd.product.model.ProductCustomer;
import com.xwd.product.service.ProductCustomerService;


/**
 * 产品会员指定表
 * @author admin
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin/product/customer")
public class ProductCustomerController extends BaseAdminController {
	
	@Autowired
	private ProductCustomerService productCustomerService;
	@Autowired
	private LogService logService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/product/productCustomer";
	}
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		Page<ProductCustomer> page = productCustomerService.findByPageRequest(pageRequest);
		logService.add(request, "查询产品会员指定表");
		this.outPageJson(response, page, true);
	}
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		AuthUser user = (AuthUser) SecurityUtils.getUser();
		ProductCustomer  entity;
		Long id = this.getLong("id");
		if(id == null){
			entity = new ProductCustomer();
			this.setFieldValues(entity, request, false);
			logService.add(request, "新增产品会员指定表");
		}else{
			entity = productCustomerService.get(id);
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			this.setFieldValues(entity, request, true);
			logService.add(request, "修改产品会员指定表");
		}
		productCustomerService.saveOrUpdate(entity);
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
		productCustomerService.deleteByIds(ids);
		logService.add(request, "删除产品会员指定表");
		this.outSuccessJson(response);
	}
	
}

