package com.xwd.mall.mobile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth.SecurityUtils;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.base.web.BaseAdminController;
import com.xwd.customer.model.Customer;
import com.xwd.log.service.LogService;
import com.xwd.mall.model.GoodsOrder;
import com.xwd.mall.model.OrderItem;
import com.xwd.mall.service.GoodsOrderService;
import com.xwd.mall.service.OrderComprisedService;
import com.xwd.mall.service.OrderItemService;
import com.xwd.msg.model.CraftsmanLogistics;
import com.xwd.msg.service.CraftsmanLogisticsService;

/**
 * 客户商品订单
 * @author admin
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/mall/goodsOrder")
public class GoodsOrderMobile extends BaseAdminController {
	
	
	@Autowired
	private LogService logService;
	@Autowired
	private GoodsOrderService goodsOrderService;
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private OrderComprisedService orderComprisedService;
	@Autowired
	private CraftsmanLogisticsService craftsmanLogisticsService;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	
	/** 
	 * 查询订单列表
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
	 * 新增订单
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		String productCode = get("productCode");
		if(StringUtils.isBlank(productCode)){
			outFailureJson(response, "产品编号为空");
			return;
		}
		String addressId = get("addressId");
		if(StringUtils.isBlank(addressId)){
			outFailureJson(response, "请选择收货地址");
			return;
		}
		String description = get("description");
		//属性
		String av = get("av");
		if(StringUtils.isBlank(av)){
			outFailureJson(response, "请选择产品属性");
			return;
		}
		//数量
		String num = get("num");
		if(StringUtils.isBlank(num)){
			outFailureJson(response, "数量不能为空");
			return;
		}
		String  amount = get("amount");
		if(StringUtils.isBlank(amount)){
			outFailureJson(response, "金额不能为空");
			return;
		}
		// 支付金额
		String paymentAmount = get("paymentAmount");
		if(StringUtils.isBlank(paymentAmount)){
			outFailureJson(response, "实付金额不能为空");
			return;
		}
		
		GoodsOrder goodsOrder = orderComprisedService.save(user,Long.valueOf(productCode) ,addressId,description, av, num,amount,paymentAmount);
		if(goodsOrder == null){
			outFailureJson(response,"提交失败");
		}else{
			outSuccessJson(response,goodsOrder);
		}
		
	}
	
	/**
	 * 查看我的订单信息
	 */
	@RequestMapping("/findOrderDetail")
	@ResponseBody
	public void findOrderDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		
		Map<GoodsOrder, List<OrderItem>> orderDetail = new HashMap<GoodsOrder, List<OrderItem>>();
		
		List<GoodsOrder> goodsOrderList = goodsOrderService.findBy("customerNo",user.getCustomerNo());
		if(goodsOrderList == null || goodsOrderList.size()<=0){
			outJson(response,"无数据信息");
			return;
		}
		for (GoodsOrder goodsOrder : goodsOrderList) {
			List<OrderItem> findBy = orderItemService.findBy("orderNo",goodsOrder.getOrderNo());
			orderDetail.put(goodsOrder, findBy);
		}
		this.outJson(response, orderDetail, true);
	}
	
	/**
	 * 取消对象.
	 * 这里接受一个名称为“ids”的字符串，id之间用英文半角的逗号“,”分隔。
	 **/
	@RequestMapping("/delete")
	@ResponseBody
	public 	void delete(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String ids = get("ids");
		goodsOrderService.deleteByIds(ids);
		logService.add(request, "");
	}
	
	/**
	 * 商品物流信息
	 */
	@RequestMapping("/findLogistics")
	@ResponseBody
	public void findLogistics(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		CraftsmanLogistics findUniqueBy = craftsmanLogisticsService.findUniqueBy("","");
		
		if(findUniqueBy == null){
			outSuccessJson(response,"无此订单物流信息");
			return;
		}
		outSuccessJson(response,findUniqueBy);
	}
	/**
	 * 取消订单
	 */
	@RequestMapping("/cancelOrder")
	@ResponseBody
	public void CancelOrder(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String orderId = get("orderId");
		if(StringUtils.isBlank(orderId)){
			outFailureJson(response, "订单Id不能为空");
			return;
		}
		GoodsOrder	entity = goodsOrderService.findUniqueBy("id",orderId);
		entity.setOrderStatus(GoodsOrder.GOODS_ORDER_STATUS_CANCEL);
		goodsOrderService.updateOrderStatus(entity);
		
		List<OrderItem> orderItemList = orderItemService.findBy("orderId",orderId);
		for (OrderItem orderItem : orderItemList) {
			orderItemService.delete(orderItem);
		}
		outSuccessJson(response);
	}
}
