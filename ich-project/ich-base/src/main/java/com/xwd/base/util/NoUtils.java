package com.xwd.base.util;

import java.util.Calendar;
import java.util.Random;

import com.frame.util.CalendarUtils;

/**
 * 单号、流水号等工具类
 * 
 * @author ljl
 *
 */
public class NoUtils {

	private static final char[] SALT_CODE = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	private static final char[] INTEGER_CODE = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	/**
	 * 生成充值单号
	 * 
	 * @return
	 */
	public static String generateDepositsNo() {
		return CalendarUtils.convertStrThree(Calendar.getInstance()) + getSalt(6);
	}

	/**
	 * 生成提现单号
	 * 
	 * @return
	 */
	public static String generateWithdrawsNo() {
		return CalendarUtils.convertStrThree(Calendar.getInstance()) + getSalt(6);
	}

	/**
	 * 初始生成sku(8位) 属性编号(4位)+0001 递增
	 * 
	 * @return
	 */
	public static Long getSku(Long code) {
		return Long.valueOf(code + "0001");
	}

	/**
	 * 获得委托编号 随机8位数
	 * 
	 * @return
	 */
	public static int getEntrustCode() {
		Random random = new Random();
		int k = random.nextInt(INTEGER_CODE.length);
		return Integer.valueOf(k + getSalt(7));
	}

	/**
	 * 交易号 规则: yyyyMMddHHmmss yyyyMMddHHmmss+002+随机六位
	 * 
	 * @return
	 */
	public static String getTradeNo() {
		return CalendarUtils.convertStrPattern(Calendar.getInstance(), "yyyyMMddHHmmss") + "002" + getSalt(6);
	}

	/**
	 * 生成订单号 yyyyMMddHHmmss+001+随机六位
	 * 
	 * @param
	 */
	public static String getOrderNo() {
		return CalendarUtils.convertStrPattern(Calendar.getInstance(), "yyyyMMddHHmmss") + "001" + getSalt(6);
	}

	/**
	 * (初始值)生成属性编号
	 * 
	 * @param length
	 * @return
	 */
	public static Long getCategoryCode(Long categoryId) {
		String categoryCode = categoryId + "";
		// int length = categoryCode.length();
		categoryCode = categoryCode + "001";
		/*
		 * switch (length) { case 1: categoryCode = categoryCode + "001"; break;
		 * case 2: categoryCode = categoryCode + "01"; break; default:
		 * categoryCode = categoryCode + "1"; }
		 */
		return Long.valueOf(categoryCode);
	}

	private static String getSalt(int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		int k = 0;
		for (int i = 0; i < length; i++) {
			k = random.nextInt(SALT_CODE.length);
			sb.append(SALT_CODE[k]);
		}
		return sb.toString();
	}

	public static String getWeek(int day) {
		String week = "";
		if (day == 1) {
			week = "一";
		} else if (day == 2) {
			week = "二";
		} else if (day == 3) {
			week = "三";
		} else if (day == 4) {
			week = "四";
		} else if (day == 5) {
			week = "五";
		} else if (day == 6) {
			week = "六";
		} else if (day == 7) {
			week = "日";
		}
		return week;
	}
}
