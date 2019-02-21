package com.xwd.trade.dto;

import java.io.Serializable;
import java.util.Calendar;

import com.frame.util.CalendarUtils;

/**
 * K线时间
 * @author ljl
 *
 */
public class KLineTime implements Serializable {

	private static final long serialVersionUID = 7560227011994419930L;

	// 开始交易时间
	private String beginTradeTime;
	// 交易时间
	private String tradeTime;
	
	private Calendar datetime;

	public String getBeginTradeTime() {
		return beginTradeTime;
	}

	public void setBeginTradeTime(String beginTradeTime) {
		this.beginTradeTime = beginTradeTime;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	
	public void setTradeTime(Long tradeTime) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(tradeTime);
		this.tradeTime = CalendarUtils.convertStrOne(c);
	}
	
	public Calendar getDatetime() {
		return datetime;
	}

	public void setDatetime(Calendar datetime) {
		this.datetime = datetime;
	}

	/**
	 * 返回1分钟显示
	 */
	public String getAsMinute() {
		if (tradeTime == null) {
			return "";
		}
		return tradeTime.substring(0, 16);
	}

	/**
	 * 返回minute分钟显示 5,15,30,60分钟计算
	 * 
	 * @return
	 */
	public String getAsMinute(int minute) {
		if (tradeTime == null) {
			return "";
		}
		int time = Integer.valueOf(tradeTime.substring(14, 16));
		time = time / minute;
		time = time * minute;
		if (time < 10) {
			return tradeTime.substring(0, 14) + "0" + time;
		} else {
			return tradeTime.substring(0, 14) + time;
		}
	}

	/**
	 * 根据起始时间计算分钟 主要用于60分钟计算
	 * 
	 * @param startMinute
	 *            起始时间
	 * @param minute
	 * @return
	 */
	public String getAsMinuteFromBeginTime(int minute) {
		if (tradeTime == null || beginTradeTime == null) {
			return "";
		}
		String str = tradeTime.substring(11, 16);
		String[] s = str.split(":");
		String[] s1 = beginTradeTime.split(":");
		int n = Integer.valueOf(s[0]) * 60 + Integer.valueOf(s[1]);
		int b = Integer.valueOf(s1[0]) * 60 + Integer.valueOf(s1[1]);
		// 分钟差
		int d = n - b;
		if (minute < 60) {
			// 多少个
			d = d / minute;
			// 总时间
			d = d * minute + b;
		} else {
			d = d / 60;
			d = d * 60 + b;
		}
		n = d / 60;
		b = d % 60;
		if (n < 10) {
			if (b < 10) {
				return tradeTime.substring(0, 11) + "0" + n + ":0" + b;
			} else {
				return tradeTime.substring(0, 11) + "0" + n + ":" + b;
			}
		} else {
			if (b < 10) {
				return tradeTime.substring(0, 11) + n + ":0" + b;
			} else {
				return tradeTime.substring(0, 11) + n + ":" + b;
			}
		}
	}
	
	public String getDay(){
		return CalendarUtils.convertStrPattern(this.datetime, "yyyy-MM-dd");
	}
	
	/**
	 * 获得交易本周一日期
	 * @return
	 */
	public String getWeekFirstDay(){
		int dayOfWeek = this.datetime.get(Calendar.DAY_OF_WEEK);
		int week = this.datetime.get(Calendar.WEEK_OF_MONTH);
		if(dayOfWeek == 1){
			return CalendarUtils.convertStrPattern(this.datetime, "yyyy-MM") + "-" + week;
		}
		if(dayOfWeek == 0){
			dayOfWeek = 7;
		}
		Calendar c = CalendarUtils.getAddDay(this.tradeTime, 1 - dayOfWeek);
		return CalendarUtils.convertStrPattern(c, "yyyy-MM") + "-" + week;
	}
	
	/**
	 * 获取每月第一天
	 * @return
	 */
	public String getFirstDayOfMonth(){
		return this.tradeTime.substring(0, 7);
	}
	
	/**
	 * 获取每季度第一天
	 * @return
	 */
	public String getFirstDayOfQuarter(){
		int month = this.datetime.get(Calendar.MONTH);
		if(month < 3){
			return this.tradeTime.substring(0, 5) + "1";
		} else if(month < 6){
			return this.tradeTime.substring(0, 5) + "2";
		} else if(month < 9){
			return this.tradeTime.substring(0, 5) + "3";
		} else {
			return this.tradeTime.substring(0, 5) + "4";
		}
	}

	/**
	 * 获取每月第一天
	 * @return
	 */
	public String getFirstDayOfYear(){
		return this.tradeTime.substring(0, 4);
	}
	
}
