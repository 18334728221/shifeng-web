package com.xwd.order.mobile;

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
import com.xwd.base.web.BaseAdminController;
import com.xwd.log.service.LogService;
import com.xwd.trade.model.TradeOrder;
import com.xwd.trade.service.TradeOrderService;

/**
 * 交易订单
 * @author dong
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/order/tradeMobile")
public class OrderTradeMobile extends BaseAdminController{
	@Autowired
	private LogService logService;
	@Autowired
	private TradeOrderService tradeOrderService;
	/*
	 * 查询交易单列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = new PageRequest<HashMap<String, Object>>();
		HashMap<String, Object> filters = new HashMap<String, Object>();
		pageRequest.setFilters(filters);
		int pageNumber = 1;
		pageRequest.setPageSize(20);
		Page<TradeOrder> page = tradeOrderService.findByPageRequest(pageRequest);
		this.outPageJson(response, page, true);
	}
}
