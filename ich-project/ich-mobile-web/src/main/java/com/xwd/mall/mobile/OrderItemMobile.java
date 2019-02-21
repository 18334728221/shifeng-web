package com.xwd.mall.mobile;
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
import com.xwd.mall.model.OrderItem;
import com.xwd.mall.service.OrderItemService;

/**
 * 订单项目表
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/mall/orderItem")
public class OrderItemMobile extends BaseAdminController {
	
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private LogService logService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/mall/orderItem";
	}
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Page<OrderItem> page = orderItemService.findByPageRequest(pageRequest);
		logService.add(request, "查询 订单项目表");
		this.outPageJson(response, page, true);
	}
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		OrderItem  entity;
		Long id = this.getLong("id");
		if(id == null){
			entity = new OrderItem();
			this.setFieldValues(entity, request, false);
			logService.add(request, "新增订单项目表");
		}else{
			entity = orderItemService.get(id);
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			logService.add(request, "修改订单项目表");
			this.setFieldValues(entity, request, true);
		}
		orderItemService.saveOrUpdate(entity);
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
		orderItemService.deleteByIds(ids);
		logService.add(request, "删除订单项目表");
		this.outSuccessJson(response);
	}
	
}

