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
import com.xwd.base.model.Activity;
import com.xwd.base.model.ImageServer;
import com.xwd.base.service.ActivityService;
import com.xwd.base.web.BaseAdminController;
import com.xwd.log.service.LogService;

/**
 * 最新活动
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/base/activity")
public class ActivityMobile extends BaseAdminController {
	
	@Autowired
	private LogService logService;
	@Autowired
	private ActivityService activityService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/base/activity";
	}
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Activity> findActivity = activityService.findActivity();
		if(findActivity !=null && findActivity.size()>0){
			ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
			for (Activity activity : findActivity) {
				activity.setImageUrl(imageServer.getImageUrl() + "/" + activity.getImageId());
			}
			this.outJson(response, findActivity);
		}else{
			this.outJson(response, "无数据信息");
		}
	}
}

