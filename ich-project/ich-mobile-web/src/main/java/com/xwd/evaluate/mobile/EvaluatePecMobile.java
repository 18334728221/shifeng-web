package com.xwd.evaluate.mobile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth.SecurityUtils;
import com.xwd.base.model.ImageServer;
import com.xwd.base.web.BaseAdminController;
import com.xwd.customer.model.Customer;
import com.xwd.evaluate.model.Evaluate;
import com.xwd.evaluate.model.EvaluateImage;
import com.xwd.evaluate.model.EvaluatePec;
import com.xwd.evaluate.model.ThumbsUp;
import com.xwd.evaluate.service.EvaluateImageService;
import com.xwd.evaluate.service.EvaluatePecService;
import com.xwd.evaluate.service.EvaluateService;
import com.xwd.evaluate.service.ThumbsUpService;
import com.xwd.log.service.LogService;
import com.xwd.product.model.ProductEvaluateContent;
import com.xwd.product.service.ProductEvaluateContentService;

/**
 * 用户产品评价内容
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/evaluate/evaluatePec")
public class EvaluatePecMobile extends BaseAdminController {
	
	@Autowired
	private EvaluatePecService evaluatePecService;
	
	@Autowired
	private ProductEvaluateContentService productEvaluateContentService;
	
	@Autowired
	private EvaluateService evaluateService;
	
	@Autowired
	private EvaluateImageService evaluateImageService;
	
	@Autowired
	private ThumbsUpService thumbsUpService;
	
	@Autowired
	private LogService logService;
	
	/** 
	 * 保存评价 
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
		String content = get("content");
		String imageId = get("imageId");
		String satisfyLevel = get("satisfyLevel");
		String serveLevel = get("serveLevel");
		String logisticsLevel = get("logisticsLevel");
		
		if(StringUtils.isBlank("content")){
			outFailureJson(response,"评价内容为空");
			return;
		}
		if(StringUtils.isBlank("productCode")){
			outFailureJson(response,"产品编号为空");
			return;
		}
		if(StringUtils.isBlank("customerNo")){
			outFailureJson(response,"顾客编号为空");
			return;
		}
		
		int commentimes = evaluatePecService.queryCommenTimes(user.getCustomerNo());//校验是否评论过
		if(commentimes>0){
			outFailureJson(response,"已评论");
			return;
		}
		
		//保存评价内容
		ProductEvaluateContent productEvaluateContent = new ProductEvaluateContent();//产品评价内容
		productEvaluateContent.setProductCode(Long.valueOf(productCode));
		productEvaluateContent.setContent(content);
		//点评次数
		int  times = productEvaluateContentService.queryTimes(Long.valueOf(productCode));//点评次数
		productEvaluateContent.setTimes(times+1);
		productEvaluateContentService.save(productEvaluateContent);
		
		//添加evaluate表
		Evaluate evaluate = new Evaluate();
		evaluate.setCustomerNo(user.getCustomerNo());
		evaluate.setProductCode(Long.valueOf(productCode));
		evaluate.setSatisfyLevel(Integer.valueOf(satisfyLevel));
		evaluate.setServeLevel(Integer.valueOf(serveLevel));
		evaluate.setLogisticsLevel(Integer.valueOf(logisticsLevel));
		evaluate.setThumbsUpNumber(0);
		evaluate.setDescription("");
		evaluateService.save(evaluate);
		
		EvaluatePec  evaluatepec = new EvaluatePec();//用户产品评价内容
		evaluatepec.setPecId(productEvaluateContent.getId());//产品评价内容ID
		evaluatepec.setCustomerNo(user.getCustomerNo());
		evaluatePecService.save(evaluatepec);
		
		EvaluateImage evaluateImage = new EvaluateImage();
		evaluateImage.setEvaluateId(evaluate.getId());
		evaluateImage.setImageId(imageId);
		evaluateImage.setImageServerId(Long.valueOf(ImageServer.IMAGE_SERVER_MALL));
		evaluateImageService.save(evaluateImage);
		
		ThumbsUp thumbsUp = new ThumbsUp();
		thumbsUp.setEvaluateId(evaluate.getId());
		thumbsUp.setCustomerNo(user.getCustomerNo());
		thumbsUpService.save(thumbsUp);
		
		this.outSuccessJson(response);
		return;
	}
}

