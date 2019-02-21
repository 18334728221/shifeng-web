package com.xwd.auth.mobile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.auth.Authenticator;
import com.auth.SecurityUtils;
import com.auth.SessionManager;
import com.auth.ThreadContext;
import com.auth.provider.ForwardingAuthRealm;
import com.xwd.account.model.Account;
import com.xwd.account.provider.AccountProvider;
import com.xwd.account.service.AccountService;
import com.xwd.base.constant.RedisBaseConstant;
import com.xwd.base.constant.SmsTemplateConstant;
import com.xwd.base.util.RealNameCheckUtil;
import com.xwd.base.web.BaseAdminController;
import com.xwd.customer.model.Customer;
import com.xwd.customer.service.CustomerService;
import com.xwd.message.service.SmsService;

/**
 * 移动端 用户相关接口
 * 
 * @author linjinliang
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/public/user")
public class PublicUserPublic extends BaseAdminController {

	private static final String MOBILEPHONE_MATCHER = "^((13[0-9])|(14[5,7])|(15[0-9])|(17[0,6-8])|(18[0-9]))\\d{8}$";

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private Authenticator authenticator;
	@Autowired
	private SmsService smsService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountProvider accountProvider;
	@Autowired
	private SessionManager<Long> sessionManager;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ForwardingAuthRealm<Long> realm;

	private static ObjectMapper OBJECT_MAPPER = null;

	/**
	 * 转义json
	 */
	private String writeString(Object obj) {
		if (OBJECT_MAPPER == null) {
			OBJECT_MAPPER = new ObjectMapper();
		}
		try {
			return OBJECT_MAPPER.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 手机登录
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = getNullParamName(request);
		if (name != null) {
			this.outFailureJson(response, name + "为空");
			return;
		}
		String mobilePhone = this.get("mobilePhone");

		if (StringUtils.isBlank(mobilePhone)) {
			this.outFailureJson(response, "请输入正确的手机号");
			return;
		}

		Customer user = customerService.findUniqueBy("mobile", mobilePhone);

		if (user == null) {
			this.outFailureJson(response, "该用户不存在");
			return;
		}
		Matcher m = Pattern.compile(MOBILEPHONE_MATCHER).matcher(mobilePhone);
		if (!m.matches()) {
			this.outFailureJson(response, "手机号码格式不正确");
			return;
		}

		String password = get("password");
		String captcha = get("captcha");
		String loginType = get("loginType");
		if ("1".equals(loginType)) { // 手机号密码登录
			Customer authUser = (Customer) sessionManager.login(mobilePhone, password, 1);
			if (authUser == null) {
				this.outFailureJson(response, "用户名或密码错误");
				return;
			}
			this.outJson(response, authUser);
			return;
		} else if ("2".equals(loginType)) { // 手机号验证码登录
			if (!StringUtils.isNotBlank(captcha)) {
				this.outFailureJson(response, "请输入验证码");
				return;
			}

			String redisCaptcha = redisTemplate.boundValueOps(RedisBaseConstant.REDIS_MOBILE_LOGIN_CAPTCHA_KEY + "_" + mobilePhone).get();

			if (!captcha.equals(redisCaptcha)) {
				this.outFailureJson(response, "验证码不匹配");
				return;
			}
			this.outJson(response, user);
			return;
		}
		this.outFailureJson(response, "请选择登录方式");
		return;
	}

	/**
	 * 获取验证码
	 * 
	 * @param mobilePhone
	 *            手机号
	 * @param type
	 *            1 注册 2 登录 3修改密码4添加银行卡验证5交易密码 6修改绑定手机验证
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/captcha")
	@ResponseBody
	public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = getNullParamName(request);
		if (name != null) {
			this.outFailureJson(response, name + "为空");
			return;
		}

		String mobilePhone = get("mobilePhone");
		if (mobilePhone == null || mobilePhone.equals("")) {
			this.outFailureJson(response, "请输入正确的手机号");
			return;
		}
		
		Matcher m = Pattern.compile(MOBILEPHONE_MATCHER).matcher(mobilePhone);
		if (!m.matches()) {
			this.outFailureJson(response, "手机号码格式不正确");
			return;
		}

		String type = get("type");
		if (!("1".equals(type) || "2".equals(type) || "3".equals(type) || "4".equals(type) || "5".equals(type) || "6".equals(type) || "7".equals(type))) {
			this.outFailureJson(response, "类型不正确");
			return;
		}

		String redisKey = null;
		Customer user = customerService.getCustomerByEmailOrMobile("principal", mobilePhone);

		if ("1".equals(type)) {// 注册获取验证码
			if (user != null) {
				this.outFailureJson(response, "该用户已存在");
				return;
			}
			redisKey = RedisBaseConstant.REDIS_MOBILE_REGISTER_CAPTCHA_KEY + "_" + mobilePhone;
		} else if ("2".equals(type)) {// 验证码登录
			if (user == null) {
				this.outFailureJson(response, "该用户不存在");
				return;
			}
			redisKey = RedisBaseConstant.REDIS_MOBILE_LOGIN_CAPTCHA_KEY + "_" + mobilePhone;
		} else if ("3".equals(type)) {// 修改密码
			if (user == null) {
				this.outFailureJson(response, "该用户不存在");
				return;
			}
			redisKey = RedisBaseConstant.REDIS_MOBILE_PWD_CAPTCHA_KEY + "_" + mobilePhone;
		} else if ("4".equals(type)) {// 添加银行卡验证
			if (user == null) {
				this.outFailureJson(response, "该用户不存在");
			}
			redisKey = RedisBaseConstant.REDIS_MOBILE_BANK_CAPTCHA_KEY + "_" + mobilePhone;

		} else if ("5".equals(type)) {// 交易密码
			if (user == null) {
				this.outFailureJson(response, "该用户不存在");
			}
			redisKey = RedisBaseConstant.REDIS_ACCOUNT_PASSWORD_KEY + "_" + mobilePhone;

		}else if ("6".equals(type)) {// 修改绑定手机
			if (user == null) {
				this.outFailureJson(response, "该用户不存在");
			}
			redisKey = RedisBaseConstant.REDIS_UPDATE_MOBILE_KEY + "_" + mobilePhone;

		}else if ("7".equals(type)) {// 重置密码
			if (user == null) {
				this.outFailureJson(response, "该用户不存在");
			}
			redisKey = RedisBaseConstant.REDIS_RESET_PASSWORD_KEY + "_" + mobilePhone;

		}
		String captcha = RandomStringUtils.randomNumeric(6);

		Customer sms = new Customer();
		sms.setMobile(mobilePhone);
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", captcha);
		//注册模板
		smsService.send(sms, mobilePhone, SmsTemplateConstant.SMS_REGISTRY, map);

		redisTemplate.boundValueOps(redisKey).set(captcha);
		System.out.println("验证码" + captcha);
		redisTemplate.expire(redisKey, 5, TimeUnit.MINUTES);
		this.outSuccessJson(response);
	}

	/**
	 * 校验验证码
	 *   1 注册 2 登录 3修改密码4添加银行卡验证5交易密码6换手机号7重置密码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/validateCaptcha")
	@ResponseBody
	public void validateCaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = getNullParamName(request);
		if (name != null) {
			this.outFailureJson(response, name + "为空");
			return;
		}
		String mobilePhone = get("mobilePhone");
		if (mobilePhone == null || mobilePhone.equals("")) {
			this.outMobileJson(response, "4", "请输入正确的手机号", "");
			return;
		}
		Matcher m = Pattern.compile(MOBILEPHONE_MATCHER).matcher(mobilePhone);
		if (!m.matches()) {
			this.outMobileJson(response, "2", "手机号码格式不正确", "");
			return;
		}
		String captcha = get("captcha");
		if (captcha == null) {
			this.outMobileJson(response, "3", "验证码为空", "");
			return;
		}
		Matcher m1 = Pattern.compile("^\\d{6}$").matcher(captcha);
		if (!m1.matches()) {
			this.outMobileJson(response, "5", "验证码格式不正确", "");
			return;
		}
		String type = get("type");
		// String appType = get("appType");
		if (type == null || !("1".equals(type) || "2".equals(type) || "3".equals(type) || "4".equals(type) || "5".equals(type) || "6".equals(type) || "7".equals(type))) {
			this.outMobileJson(response, "6", "类型不正确", "");
			return;
		}
		String redisKey = null;
		if ("1".equals(type)) {// 注册验证
			redisKey = RedisBaseConstant.REDIS_MOBILE_REGISTER_CAPTCHA_KEY + "_" + mobilePhone;
		} else if ("2".equals(type)) {// 验证码登录
			redisKey = RedisBaseConstant.REDIS_MOBILE_LOGIN_CAPTCHA_KEY + "_" + mobilePhone;
		} else if ("3".equals(type)) {// 修改密码
			redisKey = RedisBaseConstant.REDIS_MOBILE_PWD_CAPTCHA_KEY + "_" + mobilePhone;
		} else if ("4".equals(type)) {// 添加银行卡验证
			redisKey = RedisBaseConstant.REDIS_MOBILE_BANK_CAPTCHA_KEY + "_" + mobilePhone;
		} else if ("5".equals(type)) {// 交易密码
			redisKey = RedisBaseConstant.REDIS_ACCOUNT_PASSWORD_KEY + "_" + mobilePhone;
		}else if ("6".equals(type)) {// 跟换手机号密码
			redisKey = RedisBaseConstant.REDIS_UPDATE_MOBILE_KEY + "_" + mobilePhone;
		}else if("7".equals(type)){
			redisKey = RedisBaseConstant.REDIS_RESET_PASSWORD_KEY + "_" + mobilePhone;
		}
		String redisCaptcha = redisTemplate.boundValueOps(redisKey).get();

		if (captcha.equals(redisCaptcha)) {
			this.outMobileJson(response, "0", "验证通过", "");
			redisTemplate.delete(redisKey);
			return;
		} else {
			this.outMobileJson(response, "7", "验证码不一致", "");
			redisTemplate.delete(redisKey);
			return;
		}
	}

	/**
	 * 注册
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/register")
	@ResponseBody
	public void register(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = getNullParamName(request);
		if (name != null) {
			this.outMobileJson(response, "9", name + "为空", "");
			return;
		}
		// String token = get("token");
		String mobilePhone = get("mobilePhone");
		if (StringUtils.isBlank(mobilePhone)) {
			this.outFailureJson(response,"手机号为空");
			return;
		}
		Customer customer = customerService.findUniqueBy("mobile",mobilePhone);
		if (customer != null) {
			this.outMobileJson(response, "2", "该用户已存在", "");
			return;
		}
		if (mobilePhone == null || mobilePhone.equals("")) {
			this.outMobileJson(response, "4", "请输入正确的手机号", "");
			return;
		}
		Matcher m = Pattern.compile(MOBILEPHONE_MATCHER).matcher(mobilePhone);
		if (!m.matches()) {
			this.outMobileJson(response, "3", "手机号码格式不正确", "");
			return;
		}
		String password = get("password");
		// String password = "shifeng";
		String pwd = authenticator.encodeCredentials(password);

		Customer lastCustomer = customerService.findLastCustomer();
		Customer user = new Customer();
		if (lastCustomer != null && lastCustomer.getCustomerNo() != null) {
			user.setCustomerNo(lastCustomer.getCustomerNo() + 1);
		} else {
			user.setCustomerNo(10000001L);
		}
		user.setEmail("");
		user.setMobile(mobilePhone);
		user.setPassword(pwd);
		user.setLocalId(authenticator.generateId());
		int result = customerService.save(user);
		if (result == 1) {
			this.outMobileJson(response, "1", "注册成功", "");
			Account account = new Account();
			account.setBalance(new BigDecimal(0));
			//顾客编号
			account.setCustomerNo(user.getCustomerNo());
			//余额
			account.setBalance(new BigDecimal(0));
			//可提现资金
			account.setWithdrawCash(new BigDecimal(0));
			//冻结资金
			account.setBlockedFunds(new BigDecimal(0));
			//总市值
			account.setMarketValue(new BigDecimal(0));
			//总成本
			account.setCost(new BigDecimal(0));;
			//盈亏
			account.setProfitAndLoss(new BigDecimal(0));
			//买手续费
			account.setBuyPoundage(new BigDecimal(0));
			//卖手续费
			account.setSellPoundage(new BigDecimal(0));
			accountService.save(account);
			accountProvider.save(account);
		} else {
			this.outMobileJson(response, "0", "注册失败", "");
		}
		return;
	}

	/**
	 * 未注册用户 token
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/token")
	@ResponseBody
	public void token(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String token = authenticator.generateId();
		/*
		 * StudentTemp studentTemp = new StudentTemp();
		 * studentTemp.setLocalId(token);
		 * studentTemp.setCreateTime(Calendar.getInstance(TimeZone.getDefault())
		 * ); studentTemp.setMobile("");
		 * studentFacadeService.getStudentTempService().save(studentTemp);
		 */
		this.outMobileJson(response, 0, "", token);
	}

	/**
	 * 获取 PublicKey
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/publicKey")
	@ResponseBody
	public void publicKey(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// String publicKey =
		// redisTemplate.boundValueOps(RedisConstant.REDIS_MOBILE_RSA_PUBLIC_KEY).get();

		/*
		 * if(publicKey == null){ publicKey =
		 * RSAUtils.getPublicKey(RSAUtils.genKeyPair());
		 * redisTemplate.boundValueOps(RedisConstant.REDIS_MOBILE_RSA_PUBLIC_KEY
		 * ).set(publicKey);
		 * redisTemplate.expire(RedisConstant.REDIS_MOBILE_RSA_PUBLIC_KEY, 1,
		 * TimeUnit.DAYS); } this.outMobileJson(response, 0, "", publicKey);
		 */
	}

	/**
	 * 获取 PrivateKey
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/privateKey")
	@ResponseBody
	public void privateKey(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		 * String privateKey = redisTemplate.boundValueOps(RedisConstant.
		 * REDIS_MOBILE_RSA_PRIVATE_KEY).get();
		 * 
		 * if(privateKey == null){ privateKey =
		 * RSAUtils.getPrivateKey(RSAUtils.genKeyPair());
		 * redisTemplate.boundValueOps(RedisConstant.
		 * REDIS_MOBILE_RSA_PRIVATE_KEY).set(privateKey);
		 * redisTemplate.expire(RedisConstant.REDIS_MOBILE_RSA_PRIVATE_KEY, 1,
		 * TimeUnit.DAYS); }
		 * 
		 * this.outMobileJson(response, 0, "", privateKey);
		 */
	}

	/**
	 * 修改绑定手机号(用户账号)
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author sf.com
	 */
	@RequestMapping("/updateMobile")
	@ResponseBody
	public void updateMobile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = getNullParamName(request);
		if (name != null) {
			this.outMobileJson(response, "9", name + "为空", "");
			return;
		}
		Customer user = (Customer)SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		String oldMobilePhone = get("oldMobilePhone");
		if (StringUtils.isBlank(oldMobilePhone)) {
			this.outFailureJson(response, "旧手机号为空");
			return;
		}

		Customer customer = customerService.findUniqueBy("customerNo", user.getCustomerNo(), "mobilePhone", oldMobilePhone);

		if (customer == null) {
			this.outFailureJson(response, "该用户不存在");
			return;
		}

		String newMobilePhone = get("newMobilePhone");
		if (StringUtils.isBlank(newMobilePhone)) {
			this.outFailureJson(response, "新手机号为空");
			return;
		}

		if (oldMobilePhone.equals(newMobilePhone)) {
			this.outFailureJson(response, "新手机号和旧手机号相同");
			return;
		}

		customer.setMobile(newMobilePhone);
		customerService.update(customer);

		this.outSuccessJson(response, "修改绑定手机号成功");
	}

	/**
	 * 修改密码
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author sf.com
	 */
	@RequestMapping("/updatePassword")
	@ResponseBody
	public void updatePassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = getNullParamName(request);
		if (name != null) {
			this.outMobileJson(response, "9", name + "为空", "");
			return;
		}
		
		Customer user = (Customer)SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		String mobilePhone = get("mobilePhone");
		if (StringUtils.isBlank(mobilePhone)) {
			this.outFailureJson(response, "手机号为空");
			return;
		}
		
		Matcher m = Pattern.compile(MOBILEPHONE_MATCHER).matcher(mobilePhone);
		if (!m.matches()) {
			this.outFailureJson(response, "手机号码格式不正确");
			return;
		}
		String oldPassword = get("oldPassword");
		if (StringUtils.isBlank(oldPassword)) {
			this.outFailureJson(response, "旧密码为空");
			return;
		}
		Customer customer = customerService.findUniqueBy("customerNo", user.getCustomerNo(), "mobilePhone", mobilePhone);
		if (customer == null) {
			this.outFailureJson(response, "该用户不存在");
			return;
		}

		if (authenticator.credentialsMatch(oldPassword, customer.getPassword())) {
			String newPassword = get("newPassword");
			if (StringUtils.isBlank(newPassword)) {
				this.outFailureJson(response, "新密码为空");
				return;
			}
			String pwd = authenticator.encodeCredentials(newPassword);

			customer.setPassword(pwd);
			customerService.update(customer);

			this.outSuccessJson(response, "密码修改成功");
		}else{
			this.outFailureJson(response, "原密码错误");
		}
	}
	
	/**
	 * 重置登录密码(忘记密码)
	 */
	@RequestMapping("/resetPassword")
	@ResponseBody
	public void resetPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String name = getNullParamName(request);
		if (name != null) {
			this.outMobileJson(response, "9", name + "为空", "");
			return;
		}
		
		String mobilePhone = get("mobilePhone");
		if (StringUtils.isBlank(mobilePhone)) {
			this.outFailureJson(response, "手机号为空");
			return;
		}
		Matcher m = Pattern.compile(MOBILEPHONE_MATCHER).matcher(mobilePhone);
		if (!m.matches()) {
			this.outFailureJson(response, "手机号码格式不正确");
			return;
		}
		String newPassword = get("newPassword");
		if (StringUtils.isBlank(newPassword)) {
			this.outFailureJson(response, "密码为空");
			return;
		}
		String pwd = authenticator.encodeCredentials(newPassword);
		Customer customer = customerService.findUniqueBy("mobile", mobilePhone);
		
		if (customer == null) {
			this.outFailureJson(response, "该用户不存在");
			return;
		}
		customer.setPassword(pwd);
		customerService.update(customer);
		this.outSuccessJson(response, "密码重置成功");
	}
	
	/**
	 * 重置资金密码
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author sf.com
	 */
	@RequestMapping("/resetAccountPassword")
	@ResponseBody
	public void resetAccountPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String name = getNullParamName(request);
		if (name != null) {
			this.outMobileJson(response, "9", name + "为空", "");
			return;
		}
		
		String identityCardNo = get("identityCardNo");
		if (StringUtils.isBlank(identityCardNo)) {
			this.outFailureJson(response, "省份证号为空");
			return;
		}
		
		String mobilePhone = get("mobilePhone");
		if (StringUtils.isBlank(mobilePhone)) {
			this.outFailureJson(response, "手机号为空");
			return;
		}
		Matcher m = Pattern.compile(MOBILEPHONE_MATCHER).matcher(mobilePhone);
		if (!m.matches()) {
			this.outFailureJson(response, "手机号码格式不正确");
			return;
		}
		String newPassword = get("newPassword");
		if (StringUtils.isBlank(newPassword)) {
			this.outFailureJson(response, "密码为空");
			return;
		}
		String pwd = authenticator.encodeCredentials(newPassword);
		Customer customer = customerService.findUniqueBy("mobilePhone", mobilePhone,"identityCardNo",identityCardNo);
		
		if (customer == null) {
			this.outFailureJson(response, "该用户不存在");
			return;
		}
		
		customer.setAccountPassword(pwd);;
		customerService.update(customer);
		this.outSuccessJson(response, "密码重置成功");
	}

	/**
	 * 设置资金账户密码
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author sf.com
	 */
	@RequestMapping("/setAccountPassword")
	@ResponseBody
	public void setAccountPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = getNullParamName(request);
		if (name != null) {
			this.outMobileJson(response, "9", name + "为空", "");
			return;
		}
		
		Customer user = (Customer)SecurityUtils.getUser();
		
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		
		String identityCardNo = get("identityCardNo");
		if(StringUtils.isBlank(identityCardNo)){
			outFailureJson(response,"身份证号为空");
			return;
		}
		
		Customer customer1 = customerService.findUniqueBy("identityCardNo",identityCardNo);
		if(customer1 == null){
			outFailureJson(response,"请进行实名认证");
			return;
		}
		
		String accountPassword = get("accountPassword");
		if (StringUtils.isBlank(accountPassword)) {
			this.outFailureJson(response, "账户密码为空");
			return;
		}
		String pwd = authenticator.encodeCredentials(accountPassword);

		user.setAccountPassword(pwd);
		user.setIsAccountPwd(true);
		customerService.update(user);
		
		Customer customer = customerService.findUniqueBy("identityCardNo",identityCardNo);

		this.outSuccessJson(response, customer);
	}

	/**
	 * 修改资金账户密码
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author sf.com
	 */
	@RequestMapping("/updateAccountPassword")
	@ResponseBody
	public void updateAccountPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = getNullParamName(request);
		if (name != null) {
			this.outMobileJson(response, "9", name + "为空", "");
			return;
		}
		Customer user = (Customer)SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		
		
		String identityCardNo = get("identityCardNo");
		if(StringUtils.isBlank(identityCardNo)){
			outFailureJson(response,"身份证号为空");
			return;
		}
		Customer customer1 = customerService.findUniqueBy("identityCardNo",identityCardNo);
		
		if(customer1 == null){
			outFailureJson(response,"请进行实名认证");
			return;
		}
		
		String oldAccountPassword = get("oldAccountPassword");
		if (StringUtils.isBlank(oldAccountPassword)) {
			this.outFailureJson(response, "旧密码为空");
			return;
		}

		if (authenticator.credentialsMatch(oldAccountPassword, user.getAccountPassword())) {
			String accountPassword = get("accountPassword");
			if (StringUtils.isBlank(accountPassword)) {
				this.outFailureJson(response, "新密码为空");
				return;
			}
			String pwd = authenticator.encodeCredentials(accountPassword);

			user.setAccountPassword(pwd);
			customerService.update(user);
			Customer customer = customerService.findUniqueBy("identityCardNo",identityCardNo);

			this.outSuccessJson(response, customer);
		}
	}

	/*
	 * private Byte[] toByteArray(String str){ if(str == null ||
	 * "".equals(str.trim())){ return null; } String [] byteChars =
	 * str.split(","); Byte [] bytes = new Byte[byteChars.length]; for (int i =
	 * 0; i < byteChars.length; i++) { bytes[i] = Byte.valueOf(byteChars[i]); }
	 * return bytes; }
	 */

	/*
	 * private String decrypt(String str){ String result = "";
	 * 
	 * String [] byteChars = str.split(","); byte [] bytes = new
	 * byte[byteChars.length]; for (int i = 0; i < byteChars.length; i++) {
	 * bytes[i] = Byte.valueOf(byteChars[i]); }
	 * 
	 * String privateKey =
	 * redisTemplate.boundValueOps(RedisConstant.REDIS_MOBILE_RSA_PRIVATE_KEY).
	 * get(); try { result = new String(RSAUtils.decryptByPrivateKey(bytes,
	 * privateKey)); } catch (Exception e) { e.printStackTrace(); } //return
	 * result; }
	 */

	/**
	 * 接收图片
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/authuserupload")
	public void authUserUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String realFileName = null;
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			String type = multipartRequest.getParameter("fileName");
			// 文件对象
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("ff");
			String ctxPath = request.getSession().getServletContext().getRealPath("/") + "/images/logo/";
			// 创建文件夹
			File dirPath = new File(ctxPath);
			if (!dirPath.exists()) {
				dirPath.mkdir();
			}
			if (null != type && type.equals("frontImg")) {
				realFileName = "loginbannerlogo.png";
			} else if (null != type && type.equals("frontTop")) {
				realFileName = "bannerlogo.png";
			} else if (null != type && type.equals("manageImg")) {
				realFileName = "loginlogo.png";
			} else if (null != type && type.equals("indexImg")) {
				realFileName = "indexlogo.png";
			} else {
				realFileName = "adminlogo.png";
			}

			// 创建文件
			File uploadFile = new File(ctxPath + realFileName);
			FileCopyUtils.copy(file.getBytes(), uploadFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.outMobileJson(response, "0", "上传成功", "");
		return;
	}

	/**
	 * 实名认证
	 */
	@RequestMapping("/realNameCheck")
	public void realNameCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Customer user = (Customer)SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		
		String realname = get("realname");
		if (StringUtils.isBlank(realname)) {
			outFailureJson(response, "姓名为空");
			return;
		}
		String cardno = get("cardno");
		if (StringUtils.isBlank(cardno)) {
			outFailureJson(response, "身份证号为空");
			return;
		}
		Boolean checkCardno = RealNameCheckUtil.checkCardno(realname, cardno);
		if (checkCardno == true) {
			user.setName(realname);
			user.setIdentityCardNo(cardno);
			user.setIsAuthentication(true);
			customerService.update(user);
			Customer customer = customerService.findUniqueBy("identityCardNo",cardno);
			outSuccessJson(response, customer);
		} else {
			outFailureJson(response, "实名认证失败");
		}

	}

	/**
	 * 查看认证信息
	 */
	@RequestMapping("/findRealNameCheck")
	public void findRealNameCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Customer user = (Customer)SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		String mobilePhone = get("mobilePhone");
		String customerNo = get("customerNo");
		
		if (StringUtils.isBlank(mobilePhone)) {
			this.outFailureJson(response, "手机号为空");
			return;
		}

		Customer findUniqueBy = customerService.findUniqueBy("customerNo", customerNo, "mobile", mobilePhone);
		if (findUniqueBy != null) {
			outSuccessJson(response, findUniqueBy);
		} else {
			outSuccessJson(response, "无数据");
		}
		outSuccessJson(response, findUniqueBy);
	}
	
	
	@RequestMapping(value = "/logout")
	public void logoutAjax(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		
		boolean bool = sessionManager.logout();
		if (bool) {
			realm.deleteUser(SecurityUtils.getUser());
		}
		// 删除cookie
		ThreadContext.deleteCookie(request, response);
		this.outSuccessJson(response);
	}
}