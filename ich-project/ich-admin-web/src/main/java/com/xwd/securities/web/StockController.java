package com.xwd.securities.web;
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
import com.xwd.log.model.LogUser;
import com.xwd.log.service.LogService;
import com.xwd.log.service.LogUserService;
import com.xwd.securities.model.Stock;
import com.xwd.securities.service.StockService;

@Controller
@Scope("prototype")
@RequestMapping("/admin/product/stock")
public class StockController extends BaseAdminController {
	
	@Autowired
	private StockService stockService;
	
	@Autowired
	private LogService logService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/product/stock";
	}
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		Page<Stock> page = stockService.findByPageRequest(pageRequest);
		this.outPageJson(response, page, true);
	}
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		Stock  entity;
		Long id = this.getLong("id");
		if(id == null){
			entity = new Stock();
			this.setFieldValues(entity, request, false);
		}else{
			entity = stockService.get(id);
			
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			this.setFieldValues(entity, request, true);
		}
		stockService.saveOrUpdate(entity);
		logService.add(request, "修改涨跌幅，流通量");
		this.outSuccessJson(response);
	}
	
	/**
	 * 是否停盘
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/isSuspended")
	@ResponseBody
	public void IsTop(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = Long.valueOf(this.get("id")) ;
		boolean isPend = Boolean.getBoolean(this.get("isPend")); 
		if(isPend==true){
			isPend=false;
		}else{
			isPend=true;
		}
		Stock entity = stockService.get(id);
		entity.setIsSuspended(isPend);
		stockService.update(entity);
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
		stockService.deleteByIds(ids);
	}
	
}

