package com.xwd.evaluate.mobile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xwd.base.web.BaseAdminController;
import com.xwd.evaluate.model.EvaluateImage;
import com.xwd.evaluate.service.EvaluateImageService;
import com.xwd.log.service.LogService;

/**
 * 评价图片
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/evaluate/evaluateImage")
public class EvaluateImageMobile extends BaseAdminController {
	
	@Autowired
	private EvaluateImageService evaluateImageService;
	@Autowired
	private LogService logService;
	
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		EvaluateImage  entity;
		Long id = this.getLong("id");
		if(id == null){
			entity = new EvaluateImage();
			logService.add(request, "新增评价图片");
			this.setFieldValues(entity, request, false);
		}else{
			entity = evaluateImageService.get(id);
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			logService.add(request, "修改评价图片");
			this.setFieldValues(entity, request, true);
		}
		evaluateImageService.saveOrUpdate(entity);
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
		evaluateImageService.deleteByIds(ids);
		logService.add(request, "删除评价图片");
		this.outSuccessJson(response);
	}
	
}

