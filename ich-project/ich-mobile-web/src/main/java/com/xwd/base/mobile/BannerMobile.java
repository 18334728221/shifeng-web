package com.xwd.base.mobile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xwd.base.constant.BaseDataConstant;
import com.xwd.base.model.Banner;
import com.xwd.base.model.ImageServer;
import com.xwd.base.service.BannerService;
import com.xwd.base.web.BaseAdminController;

/**
 * 轮播图
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/base/banner")
public class BannerMobile  extends BaseAdminController{
	
	@Autowired
	private BannerService bannerService;
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/findHome")
	@ResponseBody
	public void findHome(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<Banner> findBanner = bannerService.findBy("type",Banner.BANNER_TYPE_CAROUSEL);
		if(findBanner !=null && findBanner.size()>0){
			ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
			for (Banner banner : findBanner) {
				banner.setImageUrl(imageServer.getImageUrl() + "/" + banner.getImageId());
			}
			this.outJson(response, findBanner);
		}else{
			this.outJson(response, "无数据信息");
		}
	}
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/findMall")
	@ResponseBody
	public void findMall(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<Banner> findBanner = bannerService.findBy("type",Banner.BANNER_TYPE_SHOW);
		if(findBanner !=null && findBanner.size()>0){
			ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
			for (Banner banner : findBanner) {
				banner.setImageUrl(imageServer.getImageUrl() + "/" + banner.getImageId());
			}
			this.outJson(response, findBanner);
		}else{
			this.outJson(response, "无数据信息");
		}
	}

}
