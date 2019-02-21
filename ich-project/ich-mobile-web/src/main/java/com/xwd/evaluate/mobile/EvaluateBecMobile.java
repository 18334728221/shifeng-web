package com.xwd.evaluate.mobile;
import java.util.HashMap;
import java.util.Map;

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
import com.xwd.base.model.EvaluateContent;
import com.xwd.base.service.EvaluateContentService;
import com.xwd.base.web.BaseAdminController;
import com.xwd.customer.model.Customer;
import com.xwd.evaluate.model.EvaluateBec;
import com.xwd.evaluate.service.EvaluateBecService;
import com.xwd.log.service.LogService;

/**
 * 用户产品购买评价
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/evaluate/evaluateBec")
public class EvaluateBecMobile extends BaseAdminController {
	
	@Autowired
	private EvaluateBecService evaluateBecService;
	
	@Autowired
	private EvaluateContentService evaluateContentService;
	
	@Autowired
	private LogService logService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/evaluate/evaluateBec";
	}
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		Page<EvaluateBec> page = evaluateBecService.findByPageRequest(pageRequest);
		logService.add(request, "查询用户产品购买评价");
		this.outPageJson(response, page, true);
	}
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		EvaluateBec  evaluateBecEntity = new EvaluateBec();
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		int  starLevel = Integer.parseInt(get("starLevel"));//星级
		
		String  content = String.valueOf(get("content"));//产品评价内容
		
		EvaluateContent evateContenEntity = new EvaluateContent();
		evateContenEntity.setContent(content);
		
		long commentimes = evaluateBecService.queryCommenTimes(user.getCustomerNo());//校验是否评论过
		if(commentimes<1){
			
			evaluateContentService.save(evateContenEntity);//产品内容评价插入
			
			Long becId = evateContenEntity.getId();//产品评价内容ID
			
			evaluateBecEntity.setBecId(becId);
			evaluateBecEntity.setCustomerNo(user.getCustomerNo());
			evaluateBecEntity.setStarLevel(starLevel);
			evaluateBecService.save(evaluateBecEntity);//用户产品购买评价
			this.outSuccessJson(response);
		}
		this.outFailureJson(response,"您已评论过");
	}
}

