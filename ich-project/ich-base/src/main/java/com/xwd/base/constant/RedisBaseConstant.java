package com.xwd.base.constant;

/**
 * redis保存相关的key
 * 
 * @author ljl
 *
 */
public class RedisBaseConstant {
	
	public final static String BASE_CATEGORY_KEY = "base_category";
	public final static String BASE_CATEGORY_PROPERTY_KEY = "base_category_property";
	public final static String BASE_SYS_CONFIG_KEY = "base_sys_config";
	public final static String BASE_IMAGE_SERVER_KEY = "base_image_server";
	public final static String BASE_FESTIVAL_KEY = "base_festival";
	
	// 地区JSON树支持
	public final static String REDIS_AREA_TREE_JSON_KEY = "area_tree_json";
	// 权限模块key
	public final static String REDIS_MODULE_PERMISSION_TREE_JSON_KEY = "module_permission_tree_json";
	//移动端登录验证码
	public final static String REDIS_MOBILE_LOGIN_CAPTCHA_KEY = "mobile_login_captcha";
	//移动端注册验证码
	public final static String REDIS_MOBILE_REGISTER_CAPTCHA_KEY = "mobile_register_captcha";
	//移动端修改密码验证码
	public final static String REDIS_MOBILE_PWD_CAPTCHA_KEY = "mobile_pwd_captcha";
	//移动端添加银行卡 验证码
	public final static String REDIS_MOBILE_BANK_CAPTCHA_KEY = "mobile_bank_captcha";
	// 购物车有效时间
	public final static String SHOPPING_CART_EFFECTIVE = "shopping_cart_effective";
	//账户密码验证码
	public final static String REDIS_ACCOUNT_PASSWORD_KEY = "account_password";
	//修改绑定手机
	public final static String REDIS_UPDATE_MOBILE_KEY = "update_mobile";
	//resetPassword
	public final static String REDIS_RESET_PASSWORD_KEY = "reset_Password";
	
}
