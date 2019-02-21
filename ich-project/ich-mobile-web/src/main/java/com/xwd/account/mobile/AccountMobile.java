package com.xwd.account.mobile;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth.SecurityUtils;
import com.xwd.account.model.Account;
import com.xwd.account.provider.AccountProvider;
import com.xwd.account.service.AccountService;
import com.xwd.base.web.BaseAdminController;
import com.xwd.customer.model.Customer;
import com.xwd.customer.model.CustomerProduct;
import com.xwd.customer.provider.CustomerProductProvider;
import com.xwd.log.service.LogService;
import com.xwd.securities.model.Stock;
import com.xwd.securities.provider.StockProvider;

@Controller
@Scope("prototype")
@RequestMapping("/mobile/account")
public class AccountMobile extends BaseAdminController {
	
	@Autowired
	private LogService logService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountProvider accountProvider;
	@Autowired
	private StockProvider stockProvider;
	@Autowired
	private CustomerProductProvider customerProductProvider;
	
	/** 
	 * 查询余额
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		Account account = accountProvider.get(user.getCustomerNo());
		if(account == null){
			account = accountService.findUniqueBy("customerNo",user.getCustomerNo());
			if(account != null){
				this.accountProvider.save(account);
			}
		}
		if(account == null){
			this.outFailureJson(response, "没有对应的账户.");
			return;
		}
		//计算总市值和盈亏
		List<CustomerProduct> list = customerProductProvider.get(user.getCustomerNo());
		BigDecimal total = new BigDecimal(0);
		BigDecimal lossAndProfit = new BigDecimal(0);
		BigDecimal temp;
		for(CustomerProduct obj : list){
			Stock stock = this.stockProvider.get(obj.getProductCode());
			//当前市值
			temp = stock.getPrice().multiply(new BigDecimal(obj.getTotalNum()));
			total = total.add(temp);
		}
		outSuccessJson(response,account);
	}
}

