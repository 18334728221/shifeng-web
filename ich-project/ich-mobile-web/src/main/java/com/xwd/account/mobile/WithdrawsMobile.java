package com.xwd.account.mobile;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth.SecurityUtils;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.account.model.Withdraws;
import com.xwd.account.service.WithdrawsService;
import com.xwd.base.util.NoUtils;
import com.xwd.base.web.BaseAdminController;
import com.xwd.customer.model.Customer;
import com.xwd.log.model.LogAccount;
import com.xwd.log.model.LogWithdraws;
import com.xwd.log.service.LogAccountService;
import com.xwd.log.service.LogService;
import com.xwd.log.service.LogWithdrawsService;

/**
 * 提现
 * 
 * @author ljl
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/account/withdraws")
public class WithdrawsMobile extends BaseAdminController {

	@Autowired
	private LogService logService;
	@Autowired
	private WithdrawsService withdrawsService;
	@RequestMapping("/index")
	public String index() {
		return "/admin/base/withdraws";
	}

	/**
	 * 查询提现记录
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		Page page = withdrawsService.findByPageRequest(pageRequest);
		this.outPageJson(response, page, true);
	}

	/**
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		
		if(StringUtils.isBlank(get("amount"))){
			outFailureJson(response,"请输入提现金额");
			return;
		}
		//提现金额
		BigDecimal amount = new BigDecimal(get("amount"));
		//手续费
		BigDecimal poundage = new BigDecimal(0.00);//new BigDecimal(get("poundage"));
		//实际提现金额
		//BigDecimal actualAmount  =  ;
		//银行账户
		Long bankId = Long.valueOf(get("bankId"));
		
		Withdraws entity = new Withdraws();
		entity.setTxNo(NoUtils.generateWithdrawsNo());
		entity.setCustomerNo(user.getCustomerNo());
		entity.setAmount(amount);
		entity.setPoundage(poundage);
		//减
		entity.setActualAmount(amount.subtract(poundage));
		entity.setBankId(bankId);
		
		//this.setFieldValues(entity, request, false);
		logService.add(request, "提现" + entity.getAmount() + ".");
		withdrawsService.save(entity);
		
		this.outSuccessJson(response);
	}

	/**
	 * 删除对象. 这里接受一个名称为“ids”的字符串，id之间用英文半角的逗号“,”分隔。
	 **/
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ids = get("ids");
		withdrawsService.deleteByIds(ids);
		logService.add(request, "删除提现");
	}

}
