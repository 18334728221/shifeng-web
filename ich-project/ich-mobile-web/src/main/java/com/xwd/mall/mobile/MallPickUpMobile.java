package com.xwd.mall.mobile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth.SecurityUtils;
import com.xwd.base.web.BaseAdminController;
import com.xwd.customer.model.Customer;
import com.xwd.log.service.LogService;
import com.xwd.mall.model.GoodsOrder;
import com.xwd.mall.model.MallPickUp;
import com.xwd.mall.model.OrderItem;
import com.xwd.mall.service.GoodsOrderService;
import com.xwd.mall.service.MallPickUpService;
import com.xwd.mall.service.OrderItemService;
import com.xwd.product.service.SkuService;

/**
 * 商城提货
 * @author admin
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/mall/pickUp")
public class MallPickUpMobile extends BaseAdminController {

	@Autowired
	private MallPickUpService mallPickUpService;
	@Autowired
	private LogService logService;
	@Autowired
	private SkuService skuService;
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private GoodsOrderService goodsOrderService;
	
	/** 
	 * 查询提货记录
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<MallPickUp> findAll = this.mallPickUpService.findAll();
		if(findAll == null|| findAll.size()<=0){
			this.outJson(response, "无数据");
			return;
		}
		this.outJson(response, findAll);
	}
	
	/** 
	 * 新增
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
			outFailureJson(response,"产品编号为空");
			return;
		}
		
		String sku = get("sku");
		if(sku==null){
			outFailureJson(response,"sku为空");
			return;
		}
		
		Long num = getLong("num");
		if(num==null){
			outFailureJson(response,"数量为空");
			return;
		}
		
		Long addressId = getLong("addressId");
		if(addressId==null){
			outFailureJson(response,"收货地址为空");
			return;
		}
		
		String pickUpTime = get("pickUpTime");
		if(StringUtils.isBlank(pickUpTime)){
			outFailureJson(response,"提货时间为空");
			return;
		}
		
		MallPickUp pickUp = new MallPickUp();
		//订单编号
		pickUp.setCode(Long.valueOf(productCode));
		//sku码
		pickUp.setSku(sku);
		//收货地址
		pickUp.setAddressId(addressId);
		//顾客编号
		pickUp.setCustomerNo(user.getCustomerNo());
		//产品数量
		pickUp.setNum(num);
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Date date =sdf.parse(pickUpTime);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		//提货日期
		pickUp.setPickUpTime(calendar);
		mallPickUpService.save(pickUp);
		this.outSuccessJson(response);
	}
	
	/**
	 * 取消对象.
	 * 这里接受一个名称为“ids”的字符串，id之间用英文半角的逗号“,”分隔。
	 **/
	@RequestMapping("/delete")
	@ResponseBody
	public 	void delete(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String ids = get("ids");
		mallPickUpService.deleteByIds(ids);
		logService.add(request, "");
	}
	
	/**
	 * 确认收货
	 */
	@RequestMapping("/affirmPickUp")
	@ResponseBody
	public 	void affirmPickUp(HttpServletRequest request, HttpServletResponse response)throws Exception {
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			this.outFailureJson(response,"请登录");
			return;
		}
		String orderNo = get("orderNo");
		if(StringUtils.isBlank(orderNo)){
			this.outFailureJson(response,"订单编号为空");
			return;
		}
		
		String sku = get("sku");
		OrderItem orderItem = orderItemService.findUniqueBy("sku",sku,"orderNo",orderNo);
		if(orderItem == null || orderItem.getProductCode() == null){
			this.outFailureJson(response,"无此产品信息");
			return;
		}
		GoodsOrder goodsOrder = goodsOrderService.get(orderItem.getOrderId());
		Calendar c = Calendar.getInstance();
		MallPickUp pickUp = new MallPickUp();
		pickUp.setAddressId(goodsOrder.getAddressId());
		pickUp.setCode(orderItem.getProductCode());
		pickUp.setCustomerNo(user.getCustomerNo());
		pickUp.setDescription("");
		pickUp.setNum(Long.valueOf(orderItem.getNum()));
		pickUp.setSku(sku);
		pickUp.setPickUpTime(c);
		pickUp.setReceiveTime(c);
		mallPickUpService.save(pickUp);
		this.outSuccessJson(response);
	}
}

