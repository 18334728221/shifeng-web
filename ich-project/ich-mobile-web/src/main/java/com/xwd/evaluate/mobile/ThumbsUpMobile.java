package com.xwd.evaluate.mobile;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth.SecurityUtils;
import com.xwd.base.web.BaseAdminController;
import com.xwd.customer.model.Customer;
import com.xwd.evaluate.model.Evaluate;
import com.xwd.evaluate.model.ThumbsUp;
import com.xwd.evaluate.service.EvaluateService;
import com.xwd.evaluate.service.ThumbsUpService;
import com.xwd.log.service.LogService;

/**
 * 点赞表
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/evaluate/thumbsUp")
public class ThumbsUpMobile extends BaseAdminController {
	
	@Autowired
	private ThumbsUpService thumbsUpService;
	@Autowired
	private EvaluateService evaluateService;
	@Autowired
	private LogService logService;
	
	
	/** 
	 * 查询点赞数量
	 * 
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		String evaluateId = get("evaluateId");
		if(StringUtils.isBlank(evaluateId)){
			outFailureJson(response,"评价id为空");
			return;
		}
		
		List<ThumbsUp> findBy = thumbsUpService.findBy("evaluateId",evaluateId,"customerNo",user.getCustomerNo());
		if(findBy!=null&&findBy.size()>0){
			//已点赞
			this.outJson(response, "0");
			return;
		}else{
			//未点赞
			this.outJson(response, "1");
			return;
		}
	}
	
	/** 
	 * 查询点赞数量
	 * 
	 **/
	@RequestMapping("/count")
	@ResponseBody
	public void count(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String evaluateId = get("evaluateId");
		if(StringUtils.isBlank(evaluateId)){
			outFailureJson(response,"评价id为空");
			return;
		}
		Long count = thumbsUpService.count("evaluateId",evaluateId);
		this.outJson(response, count);
	}
	
	/** 
	 * 点赞
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		String productCode = get("productCode");
		
		if(StringUtils.isBlank(productCode)){
			outFailureJson(response,"产品编号为空");
			return;
		}
		
		Evaluate findUniqueBy = evaluateService.findUniqueBy("productCode",productCode,"customerNo",user.getCustomerNo());
		if(findUniqueBy==null){
			outFailureJson(response,"此品论已删除");
			return;
		}
		Long userNum = thumbsUpService.queryUserNum(user.getCustomerNo());
		ThumbsUp thumbsUp = thumbsUpService.findUniqueBy("evaluateId",findUniqueBy.getId());
		
		//点赞
		if(userNum<1){
			thumbsUp.setStatus(1);
			thumbsUpService.update(thumbsUp);
			//成功
			this.outSuccessJson(response,"已点赞");
			return;
		}else{
			//取消点赞
			thumbsUp.setStatus(0);
			thumbsUpService.update(thumbsUp);
			this.outSuccessJson(response,"已取消");
			return;
		}
	}
}

