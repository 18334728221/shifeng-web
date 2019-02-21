package com.xwd.seller.web;
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

import com.auth.Authenticator;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.frame.util.CollectionUtils;
import com.xwd.base.constant.BaseDataConstant;
import com.xwd.base.model.ImageServer;
import com.xwd.base.util.ZimgUtils;
import com.xwd.base.web.BaseAdminController;
import com.xwd.log.service.LogService;
import com.xwd.seller.model.Craftsman;
import com.xwd.seller.model.CraftsmanCategory;
import com.xwd.seller.service.CraftsmanCategoryService;
import com.xwd.seller.service.CraftsmanService;

/**
 * 手艺人
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin/seller/craftsman")
public class CraftsmanController extends BaseAdminController {
	
	@Autowired
	private CraftsmanService craftsmanService;
	@Autowired
	private CraftsmanCategoryService craftsmanCategoryService;
	@Autowired
	private LogService logService;
	@Autowired
	private Authenticator authenticator;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/seller/craftsman";
	}
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		String id= "";
		if(StringUtils.isNotBlank(id)){
			mapFilters.put("id", id);
		}
		Page<Craftsman> page = craftsmanService.findByPageRequest(pageRequest);
		logService.add(request, "");
		this.outPageJson(response, page, true);
	}
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		Craftsman  entity;
		//查询最后一条保存的数据
		Craftsman lastCraftsman = craftsmanService.findLastCraftsman();
		Long id = this.getLong("id");
		if(id == null){
			entity = new Craftsman();
			this.setFieldValues(entity, request, false);
			if(lastCraftsman == null ){
				entity.setCraftsmanNo(10000001L);
			}else{
				entity.setCraftsmanNo(lastCraftsman.getCraftsmanNo()+1);
			}
			String password = authenticator.encodeCredentials("000000");
			entity.setPassword(password);
			logService.add(request, "新手艺人信息");
			
		}else{
			entity = craftsmanService.get(id);
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			logService.add(request, "修改产品信息");
			this.setFieldValues(entity, request, true);
		}
		craftsmanService.saveOrUpdate(entity);
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
		craftsmanService.deleteByIds(ids);
		logService.add(request, "");
		this.outSuccessJson(response);
	}
	
	/**
	 * 保存手艺人关联的产品种类
	 * @param userId 用户Id
	 * @param roleIds 分配角色Id集合
	 **/
	@RequestMapping("/saveCraftsmanCategory")
	@ResponseBody
	public  void  saveCraftsmanCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//删除非内定角色
		Long craftsmanNo = getLong("craftsmanNo");
		if(null != craftsmanNo){
			List<CraftsmanCategory> list = craftsmanCategoryService.findByCraftsmanNo(craftsmanNo);
			for(CraftsmanCategory obj : list){
				craftsmanCategoryService.deleteBy("craftsmanNo", obj.getCraftsmanNo(), "categoryId", obj.getCategoryId());
			}
		}
		String categoryIds = get("categoryIds");
		if(StringUtils.isNotBlank(categoryIds)){
			List<Long> roleList = CollectionUtils.splitAsLong(categoryIds);
			for(Long categoryId : roleList){
				CraftsmanCategory craftsmanCategory = new CraftsmanCategory();
				craftsmanCategory.setCategoryId(categoryId);
				craftsmanCategory.setCraftsmanNo(craftsmanNo);
				craftsmanCategoryService.save(craftsmanCategory);
			}
		}
		logService.add(request, "添加分配产品种类操作");
		this.outSuccessJson(response);
	}
	
	
	/**
	 * 上传页面
	 * @return
	 */
	@RequestMapping("/toUploadCard")
	public String toUploadImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = this.get("id");
		request.setAttribute("id", id);
		//@PathVariable("id") String id,
		return "/admin/seller/uploadCard";
	}
	
	/**
	 * 图片上传
	 * 接收图片
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
		//接收参数  产品id
		String id = multipartRequest.getParameter("id").toString();
		byte[] imgData = file.getBytes();
		//查询产品信息
		//Product product = productService.findUniqueBy("id",Long.valueOf(productId));
		String retJson = ZimgUtils.sendPost(uploadUrl, imgData, fileType);
		
		int tIndex = retJson.indexOf("md5");
		//插入手艺人表
		Craftsman craftsman = craftsmanService.get(Long.valueOf(id));
		if(craftsman != null){
			craftsman.setImageServerId(imageServer.getId());
			//正面
			craftsman.setCardPositiveImageId(retJson.substring(tIndex + 6, tIndex + 38));
			craftsmanService.update(craftsman);
		}
		
		
		this.outSuccessJson(response,imageServer.getImageUrl()+craftsman.getCardPositiveImageId()); 
	}
	
	
}

