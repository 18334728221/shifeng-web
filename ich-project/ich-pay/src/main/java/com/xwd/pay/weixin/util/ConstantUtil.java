package com.xwd.pay.weixin.util;

public class ConstantUtil {
	/**
	 * 商家可以考虑读取配置文件
	 */
	
	//初始化
	public static String APP_ID = "wxd678efh567hg6787";//微信开发平台应用id
	//微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
	public static String MCH_ID = "1230000109";
	public static String MD5_API_KEY = "C380BEC2BFD727A4B6845133519F3AD6";
	//自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
	public static String DEVICE_INFO ="WEB";
	//交易类型 JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付
	public static String TRADE_TYPE = "NATIVE";
	//APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
	public static String SPBILL_CREATE_IP = "210.12.81.244";
	public static String NOTIFY_URL= "http://210.12.81.244/edu-admin-web/";//回调地址。测试回调必须保证外网能访问到此地址
	//微信提交url                     
	public static String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	
	public static String APP_SECRET = "db426a9829e4b49a0dcac7b4162da6b6";//应用对应的凭证
	//应用对应的密钥
	public static String APP_KEY = "L8LrMqqeGRxST5reouB0K66CaYAWpqhAVsq7ggKkxHCOastWksvuX1uvmvQclxaHoYd3ElNBrNO2DHnnzgfVG9Qs473M3DTOZug5er46FhuGofumV8H2FVR9qkjSlC5K";
	public static String PARTNER = "1900000109";//财付通商户号
	public static String PARTNER_KEY = "8934e7d15453e97507ef794cf7b0519d";//商户号对应的密钥
	public static String TOKENURL = "https://api.weixin.qq.com/cgi-bin/token";//获取access_token对应的url
	public static String GRANT_TYPE = "client_credential";//常量固定值 
	public static String EXPIRE_ERRCODE = "42001";//access_token失效后请求返回的errcode
	public static String FAIL_ERRCODE = "40001";//重复获取导致上一次获取的access_token失效,返回错误码
	public static String GATEURL = "https://api.weixin.qq.com/pay/genprepay?access_token=";//获取预支付id的接口url
	public static String ACCESS_TOKEN = "access_token";//access_token常量值
	public static String ERRORCODE = "errcode";//用来判断access_token是否失效的值
	public static String SIGN_METHOD = "sha1";//签名算法常量值
	//package常量值
	public static String packageValue = "bank_type=WX&body=%B2%E2%CA%D4&fee_type=1&input_charset=GBK&notify_url=http%3A%2F%2F127.0.0.1%3A8180%2Ftenpay_api_b2c%2FpayNotifyUrl.jsp&out_trade_no=2051571832&partner=1900000109&sign=10DA99BCB3F63EF23E4981B331B0A3EF&spbill_create_ip=127.0.0.1&time_expire=20131222091010&total_fee=1";
	public static String traceid = "testtraceid001";//测试用户id
	
	
}
