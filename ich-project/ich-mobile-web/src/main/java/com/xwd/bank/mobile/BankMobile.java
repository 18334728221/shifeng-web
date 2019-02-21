package com.xwd.bank.mobile;

import java.util.HashMap;
import java.util.List;
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
import com.xwd.account.model.AccountBank;
import com.xwd.account.service.AccountBankService;
import com.xwd.base.constant.BaseDataConstant;
import com.xwd.base.model.Bank;
import com.xwd.base.web.BaseAdminController;
import com.xwd.bean.SelectModel;
import com.xwd.customer.model.Customer;

/**
 * 银行卡管理
 * 
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/bank")
public class BankMobile extends BaseAdminController {

	@Autowired
	private AccountBankService accountBankService;

	/**
	 * 查询银行卡列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Customer user = (Customer) SecurityUtils.getUser();
		if (user == null) {
			outFailureJson(response, "请登录");
			return;
		}
		if (StringUtils.isBlank(get("code"))) {
			outFailureJson(response, "银行卡号为空");
			return;
		}
		String bankCode = get("code");
		Map<String, Bank> map = BaseDataConstant.DD_BANK_MAP;// 遍历缓存中的map
		Map<String, Bank> bankMap = new HashMap<String, Bank>();
		SelectModel cdModel = new SelectModel();
		for (String key : map.keySet()) {
			if (bankCode.contains(key)) {
				Bank bank = BaseDataConstant.DD_BANK_MAP.get(key);
				bankMap.put(key, bank);
				cdModel.setText(key);
				cdModel.setValue(bank.getName().toString());
			}
		}
		this.outSuccessJson(response, cdModel);
		return;

	}

	/**
	 * 查询当前登录人持有银行卡列表
	 **/
	@RequestMapping("/findUserBank")
	@ResponseBody
	public void findUserBank(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Customer user = (Customer) SecurityUtils.getUser();
		if (user == null) {
			outFailureJson(response, "请登录");
			return;
		}
		List<AccountBank> listAcount = accountBankService.findBy("customerNo", user.getCustomerNo());
		this.outJson(response, listAcount);
	}
}
