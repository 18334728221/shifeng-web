package com.xwd.base.mobile;
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
import com.xwd.customer.model.CustomerCollection;
import com.xwd.customer.service.CustomerCollectionService;
import com.xwd.evaluate.model.Evaluate;
import com.xwd.evaluate.service.EvaluateService;
import com.xwd.log.service.LogService;
import com.xwd.product.model.Product;
import com.xwd.product.service.ProductService;
import com.xwd.seller.model.Craftsman;
import com.xwd.seller.service.CraftsmanService;


/**
 * 手艺人 移动端访问action层
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/base/craftsma")
public class CraftsmaMobile extends BaseAdminController {
	
	@Autowired
	private LogService logService;
	@Autowired
	private CraftsmanService craftsmanService;
	@Autowired
	private ProductService productService;
	@Autowired
	private EvaluateService evaluateService;
	@Autowired
	private CustomerCollectionService customerCollectionService;
	
	/**
	 * 查询店铺名称
	 */
	@RequestMapping("/findShopName")
	@ResponseBody
	public void findShopName(HttpServletRequest request, HttpServletResponse response)throws Exception{
		String craftsmanNo = get("craftsmanNo");
		if(StringUtils.isNotBlank(craftsmanNo)){
			ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
			Craftsman findUniqueBy = craftsmanService.findUniqueBy("craftsmanNo",craftsmanNo);
			if(findUniqueBy != null){
				if(StringUtils.isNotBlank(findUniqueBy.getBgImageId())){
					findUniqueBy.setBgImageUrl(imageServer.getImageUrl()+"/"+findUniqueBy.getBgImageId());
				}
				if(StringUtils.isNotBlank(findUniqueBy.getHeadImageId())){
					findUniqueBy.setHeadImageUrl(imageServer.getImageUrl()+"/"+findUniqueBy.getHeadImageId());
				}
				this.outJson(response, findUniqueBy);
			}else {
				this.outFailureJson(response, "无此记录信息");
			}
		}else{
			this.outFailureJson(response, "请求参数有误");
		}
	}
	
	/**
	 * 查询当前店铺产品名称
	 */
	@RequestMapping("/findShopProduct")
	@ResponseBody
	public void findShopProduct(HttpServletRequest request, HttpServletResponse response)throws Exception{
		String craftsmanNo = get("craftsmanNo");
		if(StringUtils.isNotBlank(craftsmanNo)){
			List<Product> findBy = productService.findBy("craftsmanNo",craftsmanNo);
			if(findBy != null && findBy.size()>0){
				ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
				for (Product product : findBy) {
					List<Evaluate> findBy3 = evaluateService.findBy("productCode",product.getCode());
					List<CustomerCollection> findBy2 = customerCollectionService.findBy("productCode",product.getCode());
					if(StringUtils.isNotBlank(product.getImageId())){
						product.setImageUrl(imageServer.getId()+"/"+product.getImageId());
					}
					product.setCountCollection(findBy2.size()+"");
					product.setCountEvaluate(findBy3.size()+"");
				}
				this.outJson(response, findBy);
			}else {
				this.outFailureJson(response, "无此记录信息");
			}
		}else{
			this.outFailureJson(response, "请求参数有误");
		}
	}
	
}

