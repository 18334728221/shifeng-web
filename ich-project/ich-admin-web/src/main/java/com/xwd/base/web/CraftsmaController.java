package com.xwd.base.web;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth.Authenticator;
import com.auth.SecurityUtils;
import com.auth.constant.RoleConstant;
import com.auth.provider.ForwardingAuthRealm;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthUser;
import com.xwd.auth.model.AuthUserRole;
import com.xwd.base.constant.MessageConstant;
import com.xwd.base.model.CategoryProperty;
import com.xwd.base.util.BaseDataUtil;
import com.xwd.log.service.LogService;
import com.xwd.seller.model.Craftsman;
import com.xwd.seller.service.CraftsmanService;

/**
 * 手艺人
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin/base/craftsma")
public class CraftsmaController extends BaseAdminController {
	
	@Autowired
	private CraftsmanService craftsmanService;
	@Autowired
	private ForwardingAuthRealm<Long> realm;
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
		Page<Craftsman> page = craftsmanService.findByPageRequest(pageRequest);
		logService.add(request, "分页查询手艺人信息。");
		this.outPageJson(response, page, true);
	}
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		Craftsman  entity;
		Long id = this.getLong("id");
		if(id == null){
			logService.add(request, "新增手艺人信息。");
			entity = new Craftsman();
			this.setFieldValues(entity, request, false);
		}else{
			logService.add(request, "修改手艺人信息。");
			entity = craftsmanService.get(id);
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			this.setFieldValues(entity, request, true);
		}
		craftsmanService.saveOrUpdate(entity);
		this.outSuccessJson(response);
	}
	
	/**
	 * 保存对象.
	 **/
	/*@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Craftsman craftsman = null;
		Long id = getLong("id");
		if(null != id){
			craftsman = craftsmanService.get(id);
			//清除缓存，修改的时候
			this.realm.deleteUser(craftsman);
			craftsman.setUpdateTime(Calendar.getInstance());
			this.setFieldValues(craftsman, request, true);
			BaseDataUtil.setFieldName(craftsman);
			if(StringUtils.isNotEmpty(craftsman.getName())){
				craftsman validatorName = craftsmanService.getUserByLoginNameOrEmailOrMobile("principal",craftsman.getName(),"id",id);
				if(null != validatorName){
					this.outResultJsoni18n(response, MessageConstant.USER_VALIDATOR_NAME,"name");
					return;
				}
			}
			if(StringUtils.isNotEmpty(craftsman.getMobile())){
				AuthUser validatorMobile = craftsmanService.getUserByLoginNameOrEmailOrMobile("principal",craftsman.getMobile(),"id",id);
				if(null != validatorMobile){
					this.outResultJsoni18n(response, MessageConstant.USER_VALIDATOR_MOBILE,"mobile");
					return;
				}
			}
			if(StringUtils.isNotEmpty(craftsman.getEmail())){
				AuthUser validatorEmail = craftsmanService.getUserByLoginNameOrEmailOrMobile("principal",craftsman.getEmail(),"id",id);
				if(null != validatorEmail){
					this.outResultJsoni18n(response, MessageConstant.USER_VALIDATOR_EMAIL,"email");
					return;
				}
			}
			craftsmanService.update(craftsman);
			craftsmanRoleService.deleteBy("userId", craftsman.getId());
			logService.add(request, "更新了用户操作");
		}else{
			craftsman = new AuthUser();
			this.setFieldValues(craftsman, request, false);
			BaseDataUtil.setFieldName(craftsman);
			craftsman.setLocalId(authenticator.generateId());
			String password = authenticator.encodeCredentials("888888");
			if(null != password){
				craftsman.setPassword(password);
			}
			craftsman.setCreateTime(Calendar.getInstance());
			if(StringUtils.isNotEmpty(craftsman.getName())){
				AuthUser validatorName = craftsmanService.getUserByLoginNameOrEmailOrMobile("principal",craftsman.getName());
				if(null != validatorName){
					this.outResultJsoni18n(response, MessageConstant.USER_VALIDATOR_NAME,"name");
					return;
				}
			}
			if(StringUtils.isNotEmpty(craftsman.getMobile())){
				AuthUser validatorMobile = craftsmanService.getUserByLoginNameOrEmailOrMobile("principal",craftsman.getMobile());
				if(null != validatorMobile){
					this.outResultJsoni18n(response, MessageConstant.USER_VALIDATOR_MOBILE,"mobile");
					return;
				}
			}
			if(StringUtils.isNotEmpty(craftsman.getEmail())){
				AuthUser validatorEmail = craftsmanService.getUserByLoginNameOrEmailOrMobile("principal",craftsman.getEmail());
				if(null != validatorEmail){
					this.outResultJsoni18n(response, MessageConstant.USER_VALIDATOR_EMAIL,"email");
					return;
				}
			}
			craftsmanService.save(craftsman);
			
			craftsman = craftsmanService.findUniqueBy("name",craftsman.getName());

			AuthUserRole craftsmanRole = new AuthUserRole();
			craftsmanRole.setUserId(craftsman.getId());
			craftsmanRole.setRoleId(RoleConstant.ROLE_FRONT_VISITOR_ID);
			authUserRoleService.save(craftsmanRole);
			logService.add(request, "添加了用户操作");
		}
		this.outSuccessJson(response);
	}*/
	
	/**
	 * 删除对象.
	 * 这里接受一个名称为“ids”的字符串，id之间用英文半角的逗号“,”分隔。
	 **/
	@RequestMapping("/delete")
	@ResponseBody
	public 	void delete(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String ids = get("ids");
		craftsmanService.deleteByIds(ids);
		logService.add(request, "删除手艺人信息。");
		this.outSuccessJson(response);
	}
	
}

