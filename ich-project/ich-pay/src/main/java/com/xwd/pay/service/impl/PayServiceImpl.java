package com.xwd.pay.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.xwd.pay.service.PayService;
import com.xwd.pay.weixin.util.ConstantUtil;
import com.xwd.pay.weixin.util.HttpClientUtil;
import com.xwd.pay.weixin.util.PayCommonUtil;
import com.xwd.pay.weixin.util.XMLUtil;

@Service
public class PayServiceImpl implements PayService {
	// private static String MCHID= "";
	// private static String APPSECRET= "";
	// private static String body= "";
	/**
	 * 支付宝封装提交数据
	 */
	@Override
	public String payByAli(Map<String, String> dataMap) {
		// 支付类型
		return "";
	}

	@Override
	public String payByWeixi(Map<String, String> dataMap) {
		JSONObject retJson = new JSONObject();
		try {
			String currTime = PayCommonUtil.getCurrTime();
			String strTime = currTime.substring(8, currTime.length());
			String strRandom = PayCommonUtil.buildRandom(4) + "";
			String nonce_str = strTime + strRandom;// 生成随机字符串

			// JSONObject requestObj = JSONObject.fromObject(params);

			// 正式上线的时候价格根据订单id查询
			// String order_price = "1"; // 价格 注意：价格的单位是分

			Map<Object, Object> packageParams = new HashMap<Object, Object>();
			packageParams.put("appid", ConstantUtil.APP_ID);
			packageParams.put("mch_id", ConstantUtil.MCH_ID);
			packageParams.put("nonce_str", nonce_str);
			packageParams.put("body", dataMap.get("body"));
			packageParams.put("out_trade_no", dataMap.get("out_trade_no"));
			packageParams.put("total_fee", dataMap.get("total_fee"));
			packageParams.put("spbill_create_ip", ConstantUtil.SPBILL_CREATE_IP);
			packageParams.put("notify_url", ConstantUtil.NOTIFY_URL);
			packageParams.put("trade_type", "NATIVE");
			// Calendar nowTime = Calendar.getInstance();
			// packageParams.put("time_start",
			// PayCommonUtil.date2String(nowTime.getTime(), "yyyyMMddHHmmss"));
			// nowTime.add(Calendar.MINUTE, requestObj.getInt("expire_time"));
			// int et = Integer.parseInt(dataMap.get("expire_time"));
			// nowTime.add(Calendar.MINUTE, et);
			// packageParams.put("time_expire",
			// PayCommonUtil.date2String(nowTime.getTime(), "yyyyMMddHHmmss"));
			String sign = PayCommonUtil.createSign("UTF-8", packageParams, ConstantUtil.MD5_API_KEY);
			packageParams.put("sign", sign);// 加密

			String requestXML = PayCommonUtil.getRequestXml(packageParams);
			// logger.info("支付请求：" + requestXML);
			long startTime = System.currentTimeMillis();
			String resXml = HttpClientUtil.sendPostRequest(ConstantUtil.pay_url, requestXML, false);
			long endTime = System.currentTimeMillis();
			Integer execute_time = (int) ((endTime - startTime) / 1000);
			// logger.info("支付结果：" + resXml);
			Map map = XMLUtil.doXMLParse(resXml);

			JSONObject reportParams = new JSONObject();
			reportParams.put("url", ConstantUtil.pay_url);
			reportParams.put("execute_time", execute_time);
			reportParams.put("return_code", map.get("return_code").toString());
			reportParams.put("return_msg", map.get("return_msg").toString());
			reportParams.put("result_code", map.get("result_code").toString());
			if (map.get("err_code") != null) {
				reportParams.put("err_code", map.get("err_code").toString());
				reportParams.put("err_code_des", map.get("err_code_des").toString());
			}
			reportParams.put("out_trade_no", dataMap.get("out_trade_no"));
			// 交易保障
			// report(reportParams.toString());
			if (map.get("return_code").toString().equals("SUCCESS")
					&& map.get("result_code").toString().equals("SUCCESS")) {
				String urlCode = (String) map.get("code_url");// 微信二维码短链接
				retJson.put("code", 0);
				retJson.put("message", "下单成功.");
				retJson.put("data", urlCode);
			} else {
				retJson.put("code", 1);
				retJson.put("message", map.get("err_code_des").toString());
				retJson.put("data", "");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return retJson.toString();
	}

}
