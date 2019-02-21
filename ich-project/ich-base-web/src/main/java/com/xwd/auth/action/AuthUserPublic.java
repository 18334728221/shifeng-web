package com.xwd.auth.action;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth.Authenticator;
import com.auth.AuthorizationPrincipal;
import com.auth.SecurityUtils;
import com.auth.SessionManager;
import com.auth.ThreadContext;
import com.auth.User;
import com.auth.constant.RoleConstant;
import com.auth.provider.ForwardingAuthRealm;
import com.frame.util.CollectionUtils;
import com.xwd.auth.constant.AuthRoleConstant;
import com.xwd.base.web.BaseAdminController;
import com.xwd.log.service.LogService;

/**
 * 
 * @author linjinliang
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/public/user")
public class AuthUserPublic extends BaseAdminController {

	@Autowired
	private ForwardingAuthRealm<Long> realm;
	@Autowired
	private SessionManager<Long> sessionManager;
	@Autowired
	private Authenticator authenticator;
	@Autowired
	private LogService logService;

	@RequestMapping("/index")
	public String index() {
		return "login";
	}

	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// 如果用户存在 而且激活成功
		String rememberMe = (String) ThreadContext.get(sessionManager.getRememberMeKey());
		if (rememberMe != null) {
			if (sessionManager.login(rememberMe)) {
				this.outSuccessJson(response);
				return;
			}
		}

		HttpSession session = request.getSession();
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		rememberMe = request.getParameter("rememberMe");
		int days = 0;
		if ("1".equals(rememberMe)) {
			days = 30;
		}
		User<Long> user = null;
		try {
			user = sessionManager.login(name, password, days);
			if (user == null) {
				map.put("message", "userMsg");
				map.put("result", this.FAILURE);
				this.outJson(response, map);
				return;
			} else {
				map.put("result", this.SUCCESS);
				// 添加cookie
				ThreadContext.addCookie(response);
				logService.add(request, "登录了系统");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("message", "pwdMsg");
			this.outJson(response, map);
			return;
		}
		// 上次页面
		String forwardUrl = get("_ret");
		if (!StringUtils.isEmpty(forwardUrl)) {
			map.put("_ret", forwardUrl);
		} else {
			forwardUrl = "";
		}

		AuthorizationPrincipal ap = SecurityUtils.getAuthorizationPrincipal();
		if (ap.hasRole(AuthRoleConstant.ROLE_CUSTOMER)) {
			if (!forwardUrl.contains("/customer/")) {
				map.put("_ret", request.getContextPath() + "/customer/center");
			}
		} else if(ap.hasRole(AuthRoleConstant.CRAFTSMAN_CUSTOMER)){
			if (!forwardUrl.contains("/craftsman/")) {
				map.put("_ret", request.getContextPath() + "/craftsman/center");
			}
		}else {
			if (!forwardUrl.contains("/admin/")) {
				map.put("_ret", request.getContextPath() + "/admin/auth/user/index");
			}
		}
		this.outJson(response, map);
	}

	@RequestMapping(value = "/logoutAjax")
	public void logoutAjax(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean bool = sessionManager.logout();
		if (bool) {
			realm.deleteUser(SecurityUtils.getUser());
		}
		// 删除cookie
		ThreadContext.deleteCookie(request, response);
		this.outResultJson(response, (String) ThreadContext.get(ThreadContext.LOGOUT_URL));
	}

	/**
	 * 初始化权限
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/initPermission")
	@ResponseBody
	public void initPermission(HttpServletRequest request, HttpServletResponse response) throws IOException {
		long level = RoleConstant.getControlLevel(SecurityUtils.getAuthorizationPrincipal().getRoles());
		if (level == RoleConstant.ROLE_SUPERADMIN_ID) {
			this.outResultJson(response, "all");
			return;
		}
		String target = get("target");
		String permissions = this.realm.getOperatePermissions(SecurityUtils.getUser(), target);
		if (StringUtils.isBlank(permissions)) {
			Collection<String> operations = CollectionUtils.split(get("operations"));
			permissions = SecurityUtils.getAuthorizationPrincipal().getPermissions(target, operations);
			this.realm.saveOperatePermissions(SecurityUtils.getUser(), target, permissions);
		}
		this.outResultJson(response, permissions);
	}

}
