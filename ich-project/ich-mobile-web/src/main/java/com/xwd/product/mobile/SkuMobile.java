package com.xwd.product.mobile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xwd.base.constant.BaseDataConstant;
import com.xwd.base.model.ImageServer;
import com.xwd.base.web.BaseAdminController;
import com.xwd.log.service.LogService;
import com.xwd.product.model.Sku;
import com.xwd.product.service.ProductService;
import com.xwd.product.service.SkuService;

/**
 * 产品库存表
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/moblie/product/sku")
public class SkuMobile extends BaseAdminController {
	
	@Autowired
	private SkuService skuService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private LogService logService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/product/productSku";
	}
	
	
	/** 
	 * 查询列表(查库存)
	 **/
	@RequestMapping("/findByAv")
	@ResponseBody
	public void findByAv(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//逗号拼接产品属性id
		String av = get("av");
		String productCode = get("productCode");
		if(StringUtils.isBlank(productCode)||StringUtils.isBlank(av)){
			outFailureJson(response,"传入参数有误");
			return;
		}
		//Long productCode = productService.get(Long.valueOf(id)).getCode();
		ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
		Sku sku = skuService.findUniqueBy("av",av,"productCode",productCode);
		if(sku == null){
			outFailureJson(response,"无此数据信息");
			return;
		}
		if(sku.getImageId() != null && sku.getImageServerId() != null){
			sku.setImageUrl(imageServer.getImageUrl()+"/"+sku.getImageId());
		}
		logService.add(request, "查询产品库存");
		outJson(response, sku);
	}
}

