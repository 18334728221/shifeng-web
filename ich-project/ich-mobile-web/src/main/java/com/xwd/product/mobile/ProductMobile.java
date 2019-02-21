package com.xwd.product.mobile;
import java.util.List;

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
import com.xwd.product.model.ProductImage;
import com.xwd.product.service.ProductImageService;

/**
 * 产品
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/product/")
public class ProductMobile extends BaseAdminController {
	
	@Autowired
	private ProductImageService productImageService;
	
	
	/** 
	 * 产品图片（除主图外）
	 **/
	@RequestMapping("/findImage")
	@ResponseBody
	public void findImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String productCode = get("productCode");
		if(StringUtils.isBlank(productCode)){
			outFailureJson(response,"产品编号为空");
			return;
		}
		List<ProductImage> findBy = productImageService.findBy("productCode",productCode,"isMain",false);
		if(findBy == null || findBy.size() <= 0){
			outSuccessJson(response,"此产品无图片信息");
			return;
		}
		ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
		for (ProductImage productImage : findBy) {
			productImage.setImageUrl(imageServer.getImageUrl() + "/" + productImage.getImageId());
		}
		outSuccessJson(response,findBy);
	}
}

