package com.xwd.home.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.auth.Authenticator;
import com.xwd.base.constant.SysConfigConstant;
import com.xwd.base.service.SysConfigService;
import com.xwd.base.web.BaseAdminController;


/**
 * 
 * @author linjinliang
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/public/home")
public class HomePublic extends BaseAdminController {

	private final String WEB_AQ_COOKIE_NAME = "web_aq_cookie_name";
	@Autowired
	private Authenticator authenticator;
	@Autowired
	private SysConfigService sysConfigService;
	
	@RequestMapping("/famous")
	public String famous() {
		return "/home/famous";
	}

	@RequestMapping("/aq")
	public String answer(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		boolean bool = false;
		for (Cookie c : cookies) {
			if (c.getName().contains(WEB_AQ_COOKIE_NAME)) {
				bool = true;
				break;
			}
		}
		if (!bool) {
			Cookie cookie = new Cookie(WEB_AQ_COOKIE_NAME, authenticator.generateId());
			cookie.setHttpOnly(true);
			cookie.setPath("/");
			cookie.setMaxAge(60 * 60 * 24 * 365);
			response.addCookie(cookie);
		}
		request.setAttribute("websocketServerUrl", sysConfigService.getValue(SysConfigConstant.WEBSOCKET_SERVER_URL_KEY));
		return "/home/overall";
	}

	@RequestMapping("/about")
	public String about() {
		return "/home/about";
	}

	@RequestMapping("/service")
	public String service() {
		return "/home/service";
	}

	@RequestMapping("/settled")
	public String settled() {
		return "/home/settled";
	}

	@RequestMapping("/success/case")
	public String successcase() {
		return "/home/case";
	}

	@RequestMapping("/teaching")
	public String teaching() {
		return "/home/famous";
	}

	@RequestMapping("/downLoad")
	public String downLoad() {
		return "/home/downLoad";
	}
	
	@RequestMapping("/gal")
	public String gal() {
		return "/home/gal";
	}
	
	@RequestMapping("/fdm")
	public String fdm() {
		return "/home/fdm";
	}
	
}
