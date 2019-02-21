package com.xwd.product.web;

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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.base.constant.BaseDataConstant;
import com.xwd.base.model.CategoryProperty;
import com.xwd.base.model.ImageServer;
import com.xwd.base.service.CategoryPropertyService;
import com.xwd.base.util.NoUtils;
import com.xwd.base.util.ZimgUtils;
import com.xwd.base.web.BaseAdminController;
import com.xwd.log.service.LogService;
import com.xwd.product.model.Product;
import com.xwd.product.model.Sku;
import com.xwd.product.model.SkuImage;
import com.xwd.product.service.ProductIssueService;
import com.xwd.product.service.ProductService;
import com.xwd.product.service.SkuImageService;
import com.xwd.product.service.SkuService;

/**
 * 产品库存表
 * 
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin/product/sku")
public class SkuController extends BaseAdminController {

	@Autowired
	private SkuService skuService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductIssueService productIssueService;

	@Autowired
	private CategoryPropertyService categoryPropertyService;

	@Autowired
	private SkuImageService skuImageService;

	@Autowired
	private LogService logService;

	@RequestMapping("/index")
	public String index() {
		return "/admin/product/productSku";
	}

	/**
	 * 分配库存
	 * 
	 * @return
	 */
	@RequestMapping("/allotSku")
	public String allotSku() {
		// 产品编号
		String productCode = this.get("productCode");

		Product product = productService.findUniqueBy("code", productCode);
		// 产品流通量
		Long circulatingStock = product.getCirculatingStock();

		Long issueAmount = productIssueService.findProductAmount(Long.valueOf(productCode));
		Long skuAmount = skuService.findProductAmount(Long.valueOf(productCode));

		Long productAmount = circulatingStock - issueAmount - skuAmount;

		// 产品属性
		List<String> assemblePropertyId = categoryPropertyService.assemblePropertyId(product.getCategoryId());
		List<String> assemblePropertyName = categoryPropertyService.assemblePropertyName(product.getCategoryId());
		request.setAttribute("productAmount", productAmount);
		request.setAttribute("assemblePropertyId", assemblePropertyId);
		request.setAttribute("assemblePropertyName", assemblePropertyName);
		request.setAttribute("productCode", productCode);

		return "/admin/product/allotSku";
	}

	/**
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		Page<Sku> page = skuService.findByPageRequest(pageRequest);
		List<Sku> result = page.getResult();
		for (Sku sku : result) {
			Product findUniqueBy = productService.findUniqueBy("code", sku.getProductCode());
			// 产品名称
			sku.setProductName(findUniqueBy.getName());
			String av = sku.getAv();
			String avName = "";
			if (StringUtils.isNotBlank(av)) {
				String avs[] = av.split(",");
				for (String string : avs) {
					CategoryProperty findUniqueBy2 = categoryPropertyService.findUniqueBy("id", string);
					avName += findUniqueBy2.getValue() + ",";
				}
				sku.setAvName(avName);
			}
		}
		logService.add(request, "分页查询产品库存");
		this.outPageJson(response, page, true);
	}

	/**
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String av = request.getParameter("av");
		String productCode = request.getParameter("productCode");

		if (StringUtils.isNotBlank(av)) {
			String[] totalStocks = request.getParameter("totalStock").split("-");
			String[] avs = request.getParameter("av").split("-");
			for (int i = 0; i < avs.length; i++) {
				Product findUniqueBy = productService.findUniqueBy("code", productCode);
				// 同属性最后入库的产品
				Sku findLastSkuByProduct = skuService.findLastSkuByProduct("productCode", productCode);
				Sku sku = skuService.findUniqueBy("av", avs[i], "productCode", productCode);
				if (sku == null) {
					sku = new Sku();
					sku.setAv(avs[i]);
					sku.setProductCode(Long.valueOf(productCode));
					if (findUniqueBy != null && findUniqueBy.getCategoryCode() != null) {
						if (findLastSkuByProduct != null) {
							sku.setSku((Long.valueOf(findLastSkuByProduct.getSku()) + 1) + "");
						} else {
							sku.setSku(NoUtils.getSku(findUniqueBy.getCategoryCode()) + "");
						}
					}
					sku.setTotalStock(Long.valueOf(totalStocks[i]));
					sku.setStock(Integer.valueOf(totalStocks[i]));
					sku.setPutaway(Sku.PUTAWAY_STATUS_SHELVES);
					logService.add(request, "新增库存");
				} else {
					sku.setTotalStock(Long.valueOf(totalStocks[i]));
					logService.add(request, "修改库存");
				}
				skuService.saveOrUpdate(sku);
				this.outSuccessJson(response);
			}
		}
	}

	/**
	 * 删除对象. 这里接受一个名称为“ids”的字符串，id之间用英文半角的逗号“,”分隔。
	 **/
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ids = get("ids");
		skuService.deleteByIds(ids);
		logService.add(request, "删除库存");
		this.outSuccessJson(response);
	}

	/**
	 * 上传页面
	 * 
	 * @return
	 */
	@RequestMapping("/toUploadImage")
	public String toUploadImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = this.get("id");
		request.setAttribute("id", id);
		// @PathVariable("id") String id,
		return "/admin/product/uploadSku";
	}

	/**
	 * 图片上传 接收图片
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/uploadImage")
	public void uploadImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String fileName = multipartRequest.getParameter("fileName");
		// 文件对象
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
		ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
		String uploadUrl = imageServer.getImageUrl();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		// 接收参数 产品id
		String issueId = multipartRequest.getParameter("id").toString();
		byte[] imgData = file.getBytes();
		// 查询产品信息
		// Product product =
		// productService.findUniqueBy("id",Long.valueOf(productId));
		String retJson = ZimgUtils.sendPost(uploadUrl, imgData, fileType);

		int tIndex = retJson.indexOf("md5");
		// 插入产品图片表
		Sku findUniqueBy = skuService.findUniqueBy("id", Long.valueOf(issueId));

		/*
		 * findUniqueBy.setImageId(retJson.substring(tIndex + 6, tIndex + 38));
		 * findUniqueBy.setImageServerId(imageServer.getId());
		 * skuService.update(findUniqueBy);
		 */
		SkuImage skuImage = new SkuImage();
		skuImage.setProductCode(findUniqueBy.getProductCode());
		skuImage.setProductSkuId(Long.valueOf(issueId));
		skuImage.setImageId(retJson.substring(tIndex + 6, tIndex + 38));
		skuImage.setImageServerId(imageServer.getId());
		skuImageService.save(skuImage);

		this.outSuccessJson(response, imageServer.getImageUrl() + findUniqueBy.getImageId());
	}

}
