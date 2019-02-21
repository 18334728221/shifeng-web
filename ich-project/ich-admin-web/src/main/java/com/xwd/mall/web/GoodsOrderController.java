package com.xwd.mall.web;
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
import com.xwd.mall.model.GoodsOrder;
import com.xwd.mall.service.GoodsOrderService;

/**
 * 客户商品订单
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin/mall/goodsOrder")
public class GoodsOrderController extends BaseAdminController {
	
	@Autowired
	private GoodsOrderService goodsOrderService;
	@Autowired
	private LogService logService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/mall/goodsOrder";
	}
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		Page<GoodsOrder> page = goodsOrderService.findByPageRequest(pageRequest);
		this.outPageJson(response, page, true);
	}
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		AuthUser user = (AuthUser) SecurityUtils.getUser();
		GoodsOrder  entity;
		Long id = this.getLong("id");
		if(id == null){
			entity = new GoodsOrder();
			this.setFieldValues(entity, request, false);
			logService.add(request, "新增客户商品订单");
		}else{
			entity = goodsOrderService.get(id);
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			logService.add(request, "修改客户商品订单");
			this.setFieldValues(entity, request, true);
		}
		goodsOrderService.saveOrUpdate(entity);
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
		goodsOrderService.deleteByIds(ids);
		logService.add(request, "删除客户商品订单");
		this.outSuccessJson(response);
	}
	
}

