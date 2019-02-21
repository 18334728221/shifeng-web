package com.xwd.trade.mobile;
import java.util.Calendar;
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
import com.frame.util.CalendarUtils;
import com.xwd.base.web.BaseAdminController;
import com.xwd.customer.model.Customer;
import com.xwd.log.service.LogService;
import com.xwd.product.model.Sku;
import com.xwd.product.service.SkuService;
import com.xwd.trade.model.TradePickUp;
import com.xwd.trade.service.TradePickUpService;


/**
 * 交易提货
 * @author admin
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/trade/pickUp")
public class TradePickUpMobile extends BaseAdminController {

	@Autowired
	private TradePickUpService tradePickUpService;
	@Autowired
	private LogService logService;
	@Autowired
	private SkuService skuService;
	
	/** 
	 * 查询提货记录
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<TradePickUp> findAll = this.tradePickUpService.findAll();
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
		
		if(StringUtils.isBlank(get("av"))){
			outFailureJson(response,"产品属性");
			return;
		}
		String av = get("av").replace("_", ",");
		Sku entity = skuService.findUniqueBy("av",av,"productCode",productCode);
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
		
		TradePickUp pickUp = new TradePickUp();
		//订单编号
		pickUp.setCode(Long.valueOf(productCode));
		//sku码
		pickUp.setSku(entity.getSku());
		//收货地址
		pickUp.setAddressId(addressId);
		//顾客编号
		pickUp.setCustomerNo(user.getCustomerNo());
		//产品数量
		pickUp.setNum(num);
		//提货日期
		pickUp.setPickUpTime(Calendar.getInstance());
		tradePickUpService.save(pickUp);
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
		tradePickUpService.deleteByIds(ids);
		logService.add(request, "");
	}
	
	/**
	 * 确认收货
	 */
	@RequestMapping("/affirmPickUp")
	@ResponseBody
	public 	void affirmPickUp(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
	}
	
	/**
	 * 提货日期
	 */
	@RequestMapping("/findDate")
	@ResponseBody
	public 	void findDate(HttpServletRequest request, HttpServletResponse response)throws Exception {
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		String productCode = get("productCode");
		if(StringUtils.isBlank(productCode)){
			outFailureJson(response,"产品编号为空");
		}
		
		if(StringUtils.isBlank(get("av"))){
			outFailureJson(response,"产品属性为空");
		}
		String av = get("av").replace("_", ",");
		Sku entity = skuService.findUniqueBy("av",av);
		Calendar c = Calendar.getInstance();
		String now = CalendarUtils.convertStrPattern(c, "yyyy-MM-dd");
		List<TradePickUp> list = tradePickUpService.findBy("pickUpTime",now,"productCode",productCode,"sku",entity.getSku());
		if(!list.isEmpty() || list.size() >0){
			outFailureJson(response,"此产品当天已被提货");
			return;
		}else{
			outSuccessJson(response);
		}
	}
	
}

