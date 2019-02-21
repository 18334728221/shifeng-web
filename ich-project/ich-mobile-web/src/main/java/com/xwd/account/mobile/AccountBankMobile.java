package com.xwd.account.mobile;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth.SecurityUtils;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.xwd.account.model.AccountBank;
import com.xwd.account.service.AccountBankService;
import com.xwd.base.model.Bank;
import com.xwd.base.web.BaseAdminController;
import com.xwd.customer.model.Customer;
import com.xwd.log.service.LogService;


/**
 * 银行卡管理
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/account/bank")
public class AccountBankMobile extends BaseAdminController {
	
	@Autowired
	private LogService logService;
	@Autowired
	private AccountBankService accountBankService;
	
	/** 
	 * 查询列表
	 **/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		//Map mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		Page page = accountBankService.findByPageRequest(pageRequest);
		
		List<Bank> result = page.getResult();
		this.outJson(response, result, true);
	}
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		//姓名
		String name = get("name");
		//银行账户
		String bankAccount = get("bankAccount");
		//银行
		String bank = get("bank");
		//支行
		String branchBank = get("branchBank");
		
		List<AccountBank> listAccountBank = accountBankService.findBy("customerNo",user.getCustomerNo(),"bankAccount",bankAccount);
		if(listAccountBank.size()>0){
			this.outFailureJson(response,"不可重复添加");
			return;
		}
		AccountBank  entity = new AccountBank();
		entity.setCustomerNo(user.getCustomerNo());
		entity.setName(name);
		entity.setBankAccount(bankAccount);
		entity.setBank(bank);
		entity.setBranchBank(branchBank);
		
		logService.add(request, "新增银行卡.");
		
		accountBankService.save(entity);
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
		accountBankService.deleteByIds(ids);
		logService.add(request, "删除银行卡.");
	}
	
}

