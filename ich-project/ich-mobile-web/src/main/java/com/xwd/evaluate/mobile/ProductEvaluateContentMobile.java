package com.xwd.evaluate.mobile;

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
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.base.web.BaseAdminController;
import com.xwd.customer.model.Customer;
import com.xwd.customer.service.CustomerService;
import com.xwd.evaluate.model.Evaluate;
import com.xwd.evaluate.model.EvaluateImage;
import com.xwd.evaluate.model.EvaluatePec;
import com.xwd.evaluate.service.EvaluateImageService;
import com.xwd.evaluate.service.EvaluatePecService;
import com.xwd.evaluate.service.EvaluateService;
import com.xwd.evaluate.service.ThumbsUpService;
import com.xwd.log.service.LogService;
import com.xwd.product.model.ProductEvaluateContent;
import com.xwd.product.service.ProductEvaluateContentService;

/**
 * 产品评价内容
 * 
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/product/evaluate")
public class ProductEvaluateContentMobile extends BaseAdminController {

	@Autowired
	private ProductEvaluateContentService productEvaluateContentService;
	@Autowired
	private EvaluatePecService evaluatePecService;
	@Autowired
	private EvaluateService evaluateService;
	@Autowired
	private EvaluateImageService evaluateImageService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ThumbsUpService thumbsUpService;
	@Autowired
	private LogService logService;

	

	/**
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String productCode = get("productCode");
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		if (StringUtils.isBlank(productCode)) {
			outFailureJson(response, "内容不得为空");
			return;
		}
		
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		mapFilters.put("productCode", productCode);
		Page<ProductEvaluateContent> page = productEvaluateContentService.findByPageRequest(pageRequest);
		List<ProductEvaluateContent> result = page.getResult();
		if (result == null || result.size() < 0) {
			outSuccessJson(response, "0");
		}
		for (ProductEvaluateContent productEvaluateContent : result) {
			EvaluatePec findUniqueBy = evaluatePecService.findUniqueBy("pecId", productEvaluateContent.getId());
			if (findUniqueBy != null) {
				Customer findUniqueBy2 = customerService.findUniqueBy("customerNo", findUniqueBy.getCustomerNo());
				if (findUniqueBy2 != null) {
					productEvaluateContent.setCustomerMobile(findUniqueBy2.getMobile());
				}
			}
			Evaluate findUniqueBy3 = evaluateService.findUniqueBy("productCode", productCode);
			if (findUniqueBy3 != null) {
				// 星级
				//productEvaluateContent.setStarLevel(findUniqueBy3.getStarLevel());
				// 点赞数量
				productEvaluateContent.setThumbsUpNumber(findUniqueBy3.getThumbsUpNumber());
				EvaluateImage findUniqueBy2 = evaluateImageService.findUniqueBy("evaluateId",findUniqueBy3.getId());
				if(findUniqueBy2!=null){
					productEvaluateContent.setImageUrl("");
				}
			}
			
			Long userNum = thumbsUpService.queryUserNum(user.getCustomerNo());
			//点赞
			if(userNum<1){
				//未点赞
				productEvaluateContent.setIsThumbsUp("0");
			}else{
				//已点赞
				productEvaluateContent.setIsThumbsUp("1");
			}
		}
		logService.add(request, "查询产品评价内容");
		this.outPageJson(response, page, true);
	}
}
