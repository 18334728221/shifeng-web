package com.xwd.product.web;
import java.util.HashMap;
import java.util.List;
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
import com.xwd.auth.model.AuthUser;
import com.xwd.base.constant.BaseDataConstant;
import com.xwd.base.model.ImageServer;
import com.xwd.base.web.BaseAdminController;
import com.xwd.log.service.LogService;
import com.xwd.product.model.Product;
import com.xwd.product.model.SkuImage;
import com.xwd.product.service.ProductService;
import com.xwd.product.service.SkuImageService;

/**
 * 产品展示图片表
 * @author admin
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin/product/skuImage")
public class SkuImageController extends BaseAdminController {
	
	@Autowired
	private SkuImageService skuImageService;
	@Autowired
	private LogService logService;
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/product/productSkuImage";
	}
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		Page<SkuImage> page = skuImageService.findByPageRequest(pageRequest);
		List<SkuImage> result = page.getResult();
		ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
		for (SkuImage skuImage : result) {
			skuImage.setImageUrl(imageServer.getImageUrl()+"/"+skuImage.getImageId());
			Product findUniqueBy = productService.findUniqueBy("code",skuImage.getProductCode());
			skuImage.setProductName(findUniqueBy.getName());
		}
		logService.add(request, "分页查询产品展示图片信息");
		this.outPageJson(response, page, true);
	}
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		AuthUser user = (AuthUser) SecurityUtils.getUser();
		SkuImage  entity;
		Long id = this.getLong("id");
		if(id == null){
			entity = new SkuImage();
			this.setFieldValues(entity, request, false);
			logService.add(request, "新增产品展示图片信息");
		}else{
			entity = skuImageService.get(id);
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			this.setFieldValues(entity, request, true);
			logService.add(request, "修改产品展示图片信息");
		}
		skuImageService.saveOrUpdate(entity);
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
		skuImageService.deleteByIds(ids);
		logService.add(request, "删除产品展示图片信息");
		this.outSuccessJson(response);
	}
	
}

