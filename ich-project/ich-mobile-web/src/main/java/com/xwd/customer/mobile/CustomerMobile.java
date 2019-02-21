package com.xwd.customer.mobile;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth.SecurityUtils;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthUser;
import com.xwd.base.web.BaseAdminController;
import com.xwd.customer.model.Customer;
import com.xwd.customer.service.CustomerService;
import com.xwd.log.service.LogService;

@Controller
@Scope("prototype")
@RequestMapping("/mobile/customer")
public class CustomerMobile extends BaseAdminController {
	
	@Autowired
	private LogService logService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	Authenticator authenticator;

	@Resource
	private RedisTemplate redisTemplate;

	@RequestMapping("/register")
	public void  registerCustom(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String capta=this.get("capta");
		String mobile=this.get("mobile");
		String pwd=this.get("pwd");
		if(capta==null||mobile==null||pwd==null){
			this.outFailureJson(response,"缺少参数");
			return;
		}
		if(!customerService.isUniqueTel("mobile",mobile)){
			this.outFailureJson(response,"手机号已经被占用");
		};

		if(!capta.equals(redisTemplate.opsForValue().get(mobile))){
			this.outFailureJson(response,"手机验证码不正确");
			redisTemplate.delete(mobile);
			return;
		}
		String orgId=this.get("orgId");
		if(orgId==null){
			orgId="10";
		}
		Long custno=customerService.genUniqeCustNo(orgId);
		Customer customer=new Customer();
		customer.setCustomerNo(custno);
		customer.setMobile(mobile);
		customer.setPassword(authenticator.encodeCredentials(pwd));
		customerService.save(customer);
		customer.setMobile(mobile);
		customerService.save(customer);
		this.outSuccessJson(response);
	}
	
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		Page page = customerService.findByPageRequest(pageRequest);
		this.outPageJson(response, page, true);
	}
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		AuthUser user = (AuthUser) SecurityUtils.getUser();
		Customer  entity;
		Long id = this.getLong("id");
		if(id == null){
			entity = new Customer();
			this.setFieldValues(entity, request, false);
			logService.add(request, "");
		}else{
			entity = customerService.get(id);
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			this.setFieldValues(entity, request, true);
			logService.add(request, "");
		}
		customerService.saveOrUpdate(entity);
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
		customerService.deleteByIds(ids);
		logService.add(request, "");
	}
	
	
}

