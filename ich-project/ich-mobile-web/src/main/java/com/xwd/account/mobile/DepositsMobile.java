package com.xwd.account.mobile;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth.SecurityUtils;
import com.xwd.account.model.Account;
import com.xwd.account.model.Deposits;
import com.xwd.account.provider.AccountProvider;
import com.xwd.account.service.AccountService;
import com.xwd.account.service.DepositsService;
import com.xwd.base.util.NoUtils;
import com.xwd.base.web.BaseAdminController;
import com.xwd.customer.model.Customer;
import com.xwd.log.model.LogAccount;
import com.xwd.log.model.LogDeposits;
import com.xwd.log.service.LogAccountService;
import com.xwd.log.service.LogDepositsService;
import com.xwd.log.service.LogService;

/**
 * 充值
 * 
 * @author ljl
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/account/deposits")
public class DepositsMobile extends BaseAdminController {

	@Autowired
	private LogService logService;
	@Autowired
	private DepositsService depositsService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountProvider accountProvider;
	@Autowired
	private LogDepositsService logDepositsService;
	@Autowired
	private LogAccountService logAccountService;

	/**
	 * 查询列表
	 **/
	/*@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		Page page = depositsService.findByPageRequest(pageRequest);
		this.outPageJson(response, page, true);
	}*/

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
		String amount = get("amount");
		if(!StringUtils.isNotBlank(amount)){
			outFailureJson(response,"金额为空");
			return;
		}
		Deposits entity = new Deposits();
		entity.setTxNo(NoUtils.generateDepositsNo());
		entity.setCustomerNo(user.getCustomerNo());
		entity.setAmount(new BigDecimal(amount));
		
		entity.setUpdateTime(Calendar.getInstance());
		logService.add(request, "充值" + entity.getAmount() + "元.");
		depositsService.saveOrUpdate(entity);
		
		//获取用户账号
		Account account = accountProvider.get(user.getCustomerNo());
		//充值金额
		account.setBalance(account.getBalance().add(entity.getAmount()));
		accountProvider.save(account);
		//更新用户账户
		accountService.update(account);
		this.outSuccessJson(response);
	}

	/**
	 * 删除对象. 这里接受一个名称为“ids”的字符串，id之间用英文半角的逗号“,”分隔。
	 **/
	/*@RequestMapping("/cancel")
	@ResponseBody
	public void cancel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String 
		Deposits entity = this.depositsService.get(getLong("id"));
		entity.setFlow(Deposits.DEPOSITS_FLOW_CANCEL);
		this.depositsService.update(entity);
		logService.add(request, "撤销充值" + entity.getTxNo() + ".");
		this.outSuccessJson(response);
	}*/

}
