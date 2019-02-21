package com.xwd.customer.mobile;
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
import com.xwd.customer.model.Address;
import com.xwd.customer.model.Customer;
import com.xwd.customer.service.AddressService;
import com.xwd.customer.service.CustomerService;
import com.xwd.log.service.LogService;

/**
 * 收货地址
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/customer/address")
public class AddressMobile extends BaseAdminController {
	
	@Autowired
	private LogService logService;
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private CustomerService customerService;
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Customer user = (Customer)SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		//String customerNo = get("customerNo");
		List<Address> list = addressService.findBy("customerNo",user.getCustomerNo() );
		this.outJson(response, list);
	}
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		Customer user = (Customer)SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		
		Address  entity;
		String id = get("id");
		// 顾客编号
		//String customerNo = get("customerNo");
		// 收货人
		String consignee = get("consignee");
		// 手机号
		String mobile = get("mobile");
		// 所在地区
		String location = get("location");
		// 详细地址
		String detailedAddress = get("detailedAddress");
		// 是否默认地址 0:否 1:是
		//private Boolean isDefault;
		if(id == null){
			entity = new Address();
			entity.setCustomerNo(user.getCustomerNo());
			entity.setConsignee(consignee);
			entity.setMobile(mobile);
			entity.setLocation(location);
			entity.setDetailedAddress(detailedAddress);
			logService.add(request, "添加收货地址.");
		}else{
			entity = addressService.get(Long.valueOf(id));
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			entity.setLocation(location);
			entity.setDetailedAddress(detailedAddress);
			logService.add(request, "修改收货地址.");
		}
		addressService.saveOrUpdate(entity);
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
		addressService.deleteByIds(ids);
		logService.add(request, "删除收货地址.");
		this.outSuccessJson(response);
	}
	
	
	/**
	 * 查询默认收货地址
	 **/
	@RequestMapping("/findAddress")
	@ResponseBody
	public 	void findAddress(HttpServletRequest request, HttpServletResponse response)throws Exception {
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		
		Customer findUniqueBy2 = customerService.findUniqueBy("customerNo",user.getCustomerNo());
		if(findUniqueBy2 == null){
			outFailureJson(response,"无此数据信息");
			return;
		}
		Address findUniqueBy = addressService.findUniqueBy("customerNo",user.getCustomerNo(),"isDefault",true);
		if(findUniqueBy == null){
			outJson(response,"无此数据信息");
			return;
		}
		if(StringUtils.isNotBlank(findUniqueBy2.getName())){
			findUniqueBy.setCustomerName(findUniqueBy2.getName());
		}
		this.outJson(response, findUniqueBy);
	}
	
	
	/**
	 * 查询单个收货地址
	 **/
	@RequestMapping("/findOneAddress")
	@ResponseBody
	public 	void findOneAddress(HttpServletRequest request, HttpServletResponse response)throws Exception {
		Long id = Long.valueOf(get("id"));
		if(id == null){
			outFailureJson(response,"传入参数为空");
			return;
		}
		Address address = addressService.get(id);
		if(address == null){
			outJson(response, "无数据");
			return;
		}
		this.outJson(response, address);
	}
	
	/**
	 * 设置默认收货地址
	 **/
	@RequestMapping("/setAcquiesceAddress")
	@ResponseBody
	public 	void setAcquiesceAddress(HttpServletRequest request, HttpServletResponse response)throws Exception {
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		//收货地址id
		Long id = Long.valueOf(get("id"));
		
		List<Address> findBy = addressService.findBy("customerNo",user.getCustomerNo());
		if(findBy != null && findBy.size() >0){
			for (Address address : findBy) {
				address.setIsDefault(false);
				addressService.update(address);
			}
		}
		Address address = addressService.get(id);
		if(address == null){
			outFailureJson(response, "无此数据信息");
			return;
		}
		address.setIsDefault(true);
		addressService.update(address);
		this.outSuccessJson(response, address);
	}
	
}

