package com.xwd.base.mobile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xwd.base.constant.BaseDataConstant;
import com.xwd.base.model.Category;
import com.xwd.base.model.ImageServer;
import com.xwd.base.service.CategoryService;
import com.xwd.base.web.BaseAdminController;
import com.xwd.log.service.LogService;


/**
 * 产品类型 移动端访问action层
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/base/category")
public class CategoryMobile extends BaseAdminController {
	
	@Autowired
	private LogService logService;
	@Autowired
	private CategoryService categoryService;
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find/{pid}")
	@ResponseBody
	public void find(@PathVariable("pid") Long pid, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Category> list = new ArrayList<Category>();
		if(pid == null || pid == 0L){
			for (Entry<Long, Category> entry : BaseDataConstant.BASE_CATEGORY_MAP.entrySet()) {
				if(entry.getValue().getParentId() == null){
					entry.getValue().setImageUrl(BaseDataConstant.getImageServer(entry.getValue().getImageServerId()) + entry.getValue().getImageId());
					list.add(entry.getValue());
				}
			}
			Collections.sort(list);
		} else {
			for (Entry<Long, Category> entry : BaseDataConstant.BASE_CATEGORY_MAP.entrySet()) {
				if(entry.getValue().getParentId() == pid){
					list.add(entry.getValue());
				}
			}
		}
		outJson(response, list);
	}
	
	@RequestMapping("/findMore")
	@ResponseBody
	public void findMore(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Category> list = new ArrayList<Category>();
		for (Entry<Long, Category> entry : BaseDataConstant.BASE_CATEGORY_MAP.entrySet()) {
			list.add(entry.getValue());
		}
		outJson(response, list);
	}
	
	/**
	 * 查询产品分类列表
	 **/
	@RequestMapping("/findAllCategory")
	@ResponseBody
	public void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Category> categoryList = categoryService.findAll();
		logService.add(request, "查询产品分类列表");
		outJson(response, categoryList);
	}
	
	/**
	 * 种类主图
	 */
	@RequestMapping("/findMainImage")
	@ResponseBody
	public void findMainImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = get("id");
		if(StringUtils.isBlank(id)){
			outFailureJson(response,"参数为空");
			return;
		}
		Category category = categoryService.get(Long.valueOf(id));
		if(category == null){
			outSuccessJson(response,"无此种类信息");
			return;
		}
		ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
		String url = imageServer.getImageUrl() +"/"+ category.getMainImage();
		outJson(response, url);
	}

	
}

