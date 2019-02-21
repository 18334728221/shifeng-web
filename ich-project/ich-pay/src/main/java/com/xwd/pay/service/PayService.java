package com.xwd.pay.service;

import java.util.Map;

/**
 * 支付接口
 * @author admin
 *
 */
public interface PayService {

	public String payByAli(Map<String, String> map);

	public String payByWeixi(Map<String, String> map);
}
