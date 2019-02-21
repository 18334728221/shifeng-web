package com.xwd.pay.action;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransOrderQueryModel;
import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.xwd.base.web.BaseAdminController;
import com.xwd.log.service.LogService;
import com.xwd.mall.model.GoodsOrder;
import com.xwd.mall.model.OrderItem;
import com.xwd.mall.service.GoodsOrderService;
import com.xwd.mall.service.OrderItemService;
import com.xwd.pay.constant.AliPayConstant;
import com.xwd.pay.service.PayService;
import com.xwd.pay.weixin.util.ConstantUtil;
import com.xwd.pay.weixin.util.PayCommonUtil;
import com.xwd.pay.weixin.util.XMLUtil;

@Controller
@Scope("prototype")
@RequestMapping("/action/pay")
public class PayAction extends BaseAdminController {
	
	public static int DEFAULT_WIDTH_AND_HEIGHT = 300; 
	
	@Autowired
	private LogService logService;
	@Autowired
	private PayService payService;
	@Autowired
	private GoodsOrderService goodsOrderService;
	@Autowired
	private OrderItemService orderItemService;
	
	/**
	 * 支付宝支付提交订单
	 * @param orderNo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/ali/{orderNo}")
	public void alipay(@PathVariable String orderNo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GoodsOrder order = this.goodsOrderService.findUniqueBy("orderNo", orderNo);
		List<OrderItem> list = this.orderItemService.findBy("orderNo", orderNo);
		
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConstant.APP_URL, AliPayConstant.APP_ID, AliPayConstant.PRIVATE_KEY, "json", "utf-8", AliPayConstant.PUBLIC_KEY, "RSA2");
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
	    alipayRequest.setReturnUrl("http://www.绝艺.cn/action/pay/return");
	    alipayRequest.setNotifyUrl("http://www.绝艺.cn/action/pay/notify");//在公共参数中设置回跳和通知地址
	    Map<String, Object> map = new HashMap<String, Object>();
	    //商户订单号，商户网站订单系统中唯一订单号，必填
	    map.put("out_trade_no", orderNo);
	    map.put("product_code", "60002");
	    //付款金额，必填
	    map.put("total_amount", 0.01);
	    //订单名称，必填
	    map.put("subject", "Iphone6 16G");
	    //商品描述，可空
	    map.put("body", "Iphone6 16G");
	    
	    map.put("passback_params", "merchantBizType%3d3C%26merchantBizNo%3d2016010101111");
	    Map<String, Object> extendParamsMap = new HashMap<String, Object>();
	    extendParamsMap.put("sys_service_provider_id", "2088511833207846");
	    map.put("extend_params", extendParamsMap);
	    
	    alipayRequest.setBizContent(JSONUtils.toJSONString(map));//填充业务参数
	    String form="";
	    try {
	        form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
	    } catch (AlipayApiException e) {
	        e.printStackTrace();
	    }
	    response.setContentType("text/html;charset=utf-8");
	    response.getWriter().write(form);//直接将完整的表单html输出到页面
	    response.getWriter().flush();
	    response.getWriter().close();
	}
	
	/**
	 * 阿里支付回调地址
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/ali/return")
	public String alipayReturn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		
		boolean signVerified = AlipaySignature.rsaCheckV1(params, AliPayConstant.PUBLIC_KEY, "utf-8", "RSA2"); //调用SDK验证签名

		//——请在这里编写您的程序（以下代码仅作参考）——
		if(signVerified) {
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		
			//付款金额
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
			
			System.out.println("trade_no:"+trade_no+"<br/>out_trade_no:"+out_trade_no+"<br/>total_amount:"+total_amount);
		}else {
			System.out.println("验签失败");
		}
		return "";
	}
	
	/**
	 * 阿里支付回调地址
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/ali/notify")
	public String alipayNofity(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		
		boolean signVerified = AlipaySignature.rsaCheckV1(params, AliPayConstant.PUBLIC_KEY, "utf-8", "RSA2"); //调用SDK验证签名

		//——请在这里编写您的程序（以下代码仅作参考）——
		
		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
		if(signVerified) {//验证成功
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		
			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			
			if(trade_status.equals("TRADE_FINISHED")){
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
					
				//注意：
				//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
			}else if (trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
				
				//注意：
				//付款完成后，支付宝系统发送该交易状态通知
			}
			
			System.out.println("success");
			
		}else {//验证失败
			System.out.println("fail");
		
			//调试用，写文本函数记录程序运行情况是否正常
			//String sWord = AlipaySignature.getSignCheckContentV1(params);
			//AlipayConfig.logResult(sWord);
		}
		return "";
	}
	
	/**
	 * 阿里支付查询
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/ali/query/{orderNo}")
	public void alipayQuery(@PathVariable String orderNo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConstant.APP_URL, AliPayConstant.APP_ID, AliPayConstant.PRIVATE_KEY, "json", "utf-8", AliPayConstant.PUBLIC_KEY,"RSA2");
		AlipayTradeQueryRequest req = new AlipayTradeQueryRequest();
		Map<String, Object> map = new HashMap<String, Object>();
		//商户订单号，商户网站订单系统中唯一订单号
	    map.put("out_trade_no", orderNo);
	    //支付宝交易号
	    map.put("trade_no", "2014112611001004680");
	    //请二选一设置
	    
	    req.setBizContent(JSONUtils.toJSONString(map));
		AlipayTradeQueryResponse res = alipayClient.execute(req);
		if(res.isSuccess()){
			System.out.println("调用成功");
		} else {
			System.out.println("调用失败");
		}
	}
	
	/**
	 * 阿里支付退款
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/ali/refund/{orderNo}")
	public void alipayRefund(@PathVariable String orderNo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConstant.APP_URL, AliPayConstant.APP_ID, AliPayConstant.PRIVATE_KEY, "json", "utf-8", AliPayConstant.PUBLIC_KEY,"RSA2");
		AlipayTradeRefundRequest req = new AlipayTradeRefundRequest();
		Map<String, Object> map = new HashMap<String, Object>();
		//商户订单号，商户网站订单系统中唯一订单号
	    map.put("out_trade_no", orderNo);
	    //支付宝交易号	
	    map.put("trade_no", "2014112611001004680");
	    //请二选一设置
	    
	    //需要退款的金额，该金额不能大于订单金额，必填
	    map.put("refund_amount", 45);
	    //退款的原因说明
	    map.put("refund_reason", "正常退款");
	    //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
	    map.put("out_request_no", "HZ01RF001");
	    map.put("operator_id", "OP001");
	    map.put("store_id", "NJ_S_001");
	    map.put("terminal_id", "NJ_T_001");
	    
	    req.setBizContent(JSONUtils.toJSONString(map));
	    AlipayTradeRefundResponse  res = alipayClient.execute(req);
		if(res.isSuccess()){
			System.out.println("调用成功");
		} else {
			System.out.println("调用失败");
		}
	}
	
	/**
	 * 阿里支付退款
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/ali/refund/query/{orderNo}")
	public void alipayRefundQuery(@PathVariable String orderNo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConstant.APP_URL, AliPayConstant.APP_ID, AliPayConstant.PRIVATE_KEY, "json", "utf-8", AliPayConstant.PUBLIC_KEY,"RSA2");
		AlipayTradeFastpayRefundQueryRequest req = new AlipayTradeFastpayRefundQueryRequest();
		Map<String, Object> map = new HashMap<String, Object>();
	    map.put("out_trade_no", orderNo);
	    map.put("trade_no", "2014112611001004680");
	    map.put("out_request_no", "2014112611001004680073956707");
	    
	    req.setBizContent(JSONUtils.toJSONString(map));
	    AlipayTradeFastpayRefundQueryResponse res = alipayClient.execute(req);
		if(res.isSuccess()){
			System.out.println("调用成功");
		} else {
			System.out.println("调用失败");
		}
	}
	
	/**
     * 单笔转账到支付宝账户
     * https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.54Ty29&treeId=193&articleId=106236&docType=1
     * @param content
     * @return
     * @throws AlipayApiException
     */
    public boolean transfer(AlipayFundTransToaccountTransferModel model) throws AlipayApiException{
    	AlipayClient alipayClient = new DefaultAlipayClient(AliPayConstant.APP_URL, AliPayConstant.APP_ID, AliPayConstant.PRIVATE_KEY, "json", "utf-8", AliPayConstant.PUBLIC_KEY,"RSA2");
    	AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        request.setBizModel(model);
        AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
        
        String result = response.getBody();
        System.out.println("transfer result>"+result);
        if (response.isSuccess()) {
            return true;
        } else {
            //调用查询接口查询数据
            JSONObject jsonObject = JSONObject.parseObject(result);
            String out_biz_no = jsonObject.getJSONObject("alipay_fund_trans_toaccount_transfer_response").getString("out_biz_no");
            AlipayFundTransOrderQueryModel queryModel = new AlipayFundTransOrderQueryModel();
            model.setOutBizNo(out_biz_no);
            boolean isSuccess = transferQuery(queryModel);
            if (isSuccess) {
                return true;
            }
        }
        return false;
    }
    
    public boolean transferQuery(AlipayFundTransOrderQueryModel model) throws AlipayApiException{
    	 AlipayClient alipayClient = new DefaultAlipayClient(AliPayConstant.APP_URL, AliPayConstant.APP_ID, AliPayConstant.PRIVATE_KEY, "json", "utf-8", AliPayConstant.PUBLIC_KEY,"RSA2");
    	 AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
         request.setBizModel(model);
         AlipayFundTransOrderQueryResponse response = alipayClient.execute(request);
         System.out.println("transferQuery result>"+response.getBody());
         if(response.isSuccess()){
             return true;
         }
         return false;
    }
    
    //test
    public void transfer() {
        boolean isSuccess = false;
        String total_amount = "100";
        AlipayFundTransToaccountTransferModel model = new AlipayFundTransToaccountTransferModel();
        model.setOutBizNo("trey23243568");//生成订单号
        model.setPayeeType("ALIPAY_LOGONID");//固定值
        model.setPayeeAccount("abpkvd0206@sandbox.com");//转账收款账户
        model.setAmount(total_amount);
        model.setPayerShowName("测试退款");
        model.setPayerRealName("沙箱环境");//账户真实名称
        model.setRemark("javen测试单笔转账到支付宝");

        try {
            isSuccess = this.transfer(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 微信支付
     * 生成二维码
     * @param orderNo
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/wx/{orderNo}")
	public void wxpay(@PathVariable String orderNo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	GoodsOrder order = this.goodsOrderService.findUniqueBy("orderNo", orderNo);
		List<OrderItem> list = this.orderItemService.findBy("orderNo", orderNo);
		
		String currTime = PayCommonUtil.getCurrTime();  
	    String strTime = currTime.substring(8, currTime.length());  
	    String strRandom = PayCommonUtil.buildRandom(4) + "";  
	    String nonce_str = strTime + strRandom;    
        long time_stamp = System.currentTimeMillis() / 1000;  
        String product_id = "hd_goodsssss_10";  
        String key = ConstantUtil.MD5_API_KEY; // key  
          
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();  
        packageParams.put("appid", ConstantUtil.APP_ID);  
        packageParams.put("mch_id", ConstantUtil.MCH_ID);  
        packageParams.put("time_stamp", String.valueOf(time_stamp));  
        packageParams.put("nonce_str", nonce_str);  
        packageParams.put("product_id", product_id);  
        String sign = PayCommonUtil.createSign("UTF-8", packageParams,key);//MD5哈希  
        packageParams.put("sign", sign);   
          
        //生成参数  
        String str = toUrlParams(packageParams);  
        String payurl = ConstantUtil.pay_url + "?" + str;  
          
          
        //生成二维码  
        Map<EncodeHintType, Object>  hints = new HashMap<EncodeHintType, Object>();  
        // 指定纠错等级    
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);    
        // 指定编码格式    
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");    
        hints.put(EncodeHintType.MARGIN, 1);  
        try {  
            BitMatrix bitMatrix = new MultiFormatWriter().encode(payurl, BarcodeFormat.QR_CODE, DEFAULT_WIDTH_AND_HEIGHT, DEFAULT_WIDTH_AND_HEIGHT, hints);  
            OutputStream out = response.getOutputStream();  
            MatrixToImageWriter.writeToStream(bitMatrix, "png", out);//输出二维码  
            out.flush();  
            out.close();  
              
        } catch (WriterException e) {  
            e.printStackTrace();  
        }  
    }
    
    public String toUrlParams(SortedMap<Object, Object> packageParams){  
        //实际可以不排序  
        StringBuffer sb = new StringBuffer();    
        Set es = packageParams.entrySet();    
        Iterator it = es.iterator();    
        while (it.hasNext()) {    
            Map.Entry entry = (Map.Entry) it.next();    
            String k = (String) entry.getKey();    
            String v = (String) entry.getValue();    
            if (null != v && !"".equals(v)) {    
                sb.append(k + "=" + v + "&");    
            }    
        }  
          
        sb.deleteCharAt(sb.length()-1);//删掉最后一个&  
        return sb.toString();  
    }  

    @RequestMapping("/wx/notify")
    public void wxNotify(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	 //读取参数  
        InputStream inputStream ;  
        StringBuffer sb = new StringBuffer();  
        inputStream = request.getInputStream();  
        String s ;  
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  
        while ((s = in.readLine()) != null){  
            sb.append(s);  
        }  
        in.close();  
        inputStream.close();  
  
        //解析xml成map  
        Map<String, String> m = new HashMap<String, String>();  
        m = XMLUtil.doXMLParse(sb.toString());  
          
        //过滤空 设置 TreeMap  
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();        
        Iterator it = m.keySet().iterator();  
        while (it.hasNext()) {  
            String parameter = (String) it.next();  
            String parameterValue = m.get(parameter);  
              
            String v = "";  
            if(null != parameterValue) {  
                v = parameterValue.trim();  
            }  
            packageParams.put(parameter, v);  
        }  
          
        // 账号信息  
        String key = ConstantUtil.MD5_API_KEY; // key  
  
        //判断签名是否正确  
        if(PayCommonUtil.isTenpaySign("UTF-8", packageParams,key)) {  
            //------------------------------  
            //处理业务开始  
            //------------------------------  
            String resXml = "";  
            if("SUCCESS".equals((String)packageParams.get("result_code"))){  
                // 这里是支付成功  
                //////////执行自己的业务逻辑////////////////  
                String mch_id = (String)packageParams.get("mch_id");  
                String openid = (String)packageParams.get("openid");  
                String is_subscribe = (String)packageParams.get("is_subscribe");  
                String out_trade_no = (String)packageParams.get("out_trade_no");  
                  
                String total_fee = (String)packageParams.get("total_fee");  
                  
                //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.  
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"  
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";  
                  
            } else {  
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"  
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";  
            }  
            //------------------------------  
            //处理业务完毕  
            //------------------------------  
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());  
            out.write(resXml.getBytes());  
            out.flush();  
            out.close();  
        } else{  
            
        }  
    }
}

