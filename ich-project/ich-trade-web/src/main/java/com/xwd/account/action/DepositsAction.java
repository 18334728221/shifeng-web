package com.xwd.account.action;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.account.model.Deposits;
import com.xwd.account.service.DepositsService;
import com.xwd.base.util.NoUtils;
import com.xwd.base.web.BaseAdminController;
import com.xwd.log.service.LogService;

@Controller
@Scope("prototype")
@RequestMapping("/action/account/deposits")
public class DepositsAction extends BaseAdminController {
	
	@Autowired
	private LogService logService;
	@Autowired
	private DepositsService depositsService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/base/deposits";
	}
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		Page page = depositsService.findByPageRequest(pageRequest);
		this.outPageJson(response, page, true);
	}
	
	/**
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Deposits entity = new Deposits();
		entity.setTxNo(NoUtils.generateDepositsNo());
		this.setFieldValues(entity, request, false);
		logService.add(request, "充值" + entity.getAmount() + "元.");
		depositsService.saveOrUpdate(entity);
		this.outSuccessJson(response);
	}

	/**
	 * 删除对象. 这里接受一个名称为“ids”的字符串，id之间用英文半角的逗号“,”分隔。
	 **/
	@RequestMapping("/cancel")
	@ResponseBody
	public void cancel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Deposits entity = this.depositsService.get(getLong("id"));
		entity.setFlow(Deposits.DEPOSITS_FLOW_CANCEL);
		this.depositsService.update(entity);
		logService.add(request, "撤销充值" + entity.getTxNo() + ".");
	}
	
}

