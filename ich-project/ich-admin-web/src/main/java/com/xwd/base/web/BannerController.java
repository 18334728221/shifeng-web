package com.xwd.base.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.xwd.base.constant.BaseDataConstant;
import com.xwd.base.model.Banner;
import com.xwd.base.model.ImageServer;
import com.xwd.base.service.BannerService;
import com.xwd.base.util.ZimgUtils;

@Controller
@Scope("prototype")
@RequestMapping("/admin/base/banner")
public class BannerController extends BaseAdminController {

	@Autowired
	private BannerService bannerService;

	@RequestMapping("/index")
	public String index() {
		return "/admin/base/banner";
	}

	/**
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<Banner> findBanner = bannerService.findAll();
		if(findBanner != null && findBanner.size() >0){
			ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
			for (Banner banner : findBanner) {
				banner.setImageUrl(imageServer.getImageUrl() + "/" + banner.getImageId());
			}
		}

		this.outJson(response,findBanner);
	}

	/**
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Banner entity;
		Long id = this.getLong("id");
		if (id == null) {
			entity = new Banner();
			this.setFieldValues(entity, request, false);
		} else {
			entity = bannerService.get(id);
			if (entity == null) {
				this.outFailureJson(response);
				return;
			}
			this.setFieldValues(entity, request, true);
		}
		bannerService.saveOrUpdate(entity);
		this.outSuccessJson(response);
	}

	/**
	 * 删除对象. 这里接受一个名称为“ids”的字符串，id之间用英文半角的逗号“,”分隔。
	 **/
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ids = get("id");
		bannerService.deleteByIds(ids);
		return "redirect:/admin/base/banner/index";
	}

	/**
	 * 上传页面
	 * 
	 * @return
	 */
	@RequestMapping("/toUploadImage")
	public String toUploadImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/base/uploadImageBanner";
	}

	/**
	 * 图片上传 接收图片
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/uploadImage")
	public String uploadImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Banner banner = new Banner();

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String fileName = multipartRequest.getParameter("fileName");
		// 文件对象
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
		ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
		String uploadUrl = imageServer.getImageUrl();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		// 接收参数 产品id
		byte[] imgData = file.getBytes();
		// 查询产品信息

		String retJson = ZimgUtils.sendPost(uploadUrl, imgData, fileType);

		int tIndex = retJson.indexOf("md5");
		banner.setType(Banner.BANNER_TYPE_CAROUSEL);
		banner.setImageId(retJson.substring(tIndex + 6, tIndex + 38));
		banner.setImageServerId(imageServer.getId());
		banner.setUrl(imageServer.getImageUrl());
		bannerService.save(banner);

		//this.outSuccessJson(response, imageServer.getImageUrl() + "/" + banner.getImageId());
		return "redirect:/admin/base/banner/index";
		
	}

	/**
	 * 设置为轮播图
	 **/
	@RequestMapping("/setBanner")
	public String setBanner(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = Long.valueOf(get("id"));
		Banner entity = bannerService.get(id);
		if (entity != null && entity.getType() != null) {
			Byte type = Byte.valueOf(entity.getType());
			if (type == 0) {
				type = Banner.BANNER_TYPE_SHOW;
			} else {
				type = Banner.BANNER_TYPE_CAROUSEL;
			}
			entity.setType(type);
			bannerService.update(entity);
		}
		return "redirect:/admin/base/banner/index";
	}
}
