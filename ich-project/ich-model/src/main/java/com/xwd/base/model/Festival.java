package com.xwd.base.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 假日表
 */
public class Festival extends BaseEntity {
		
	private static final long serialVersionUID = 1473768745045812344L;
	//columns START
	//id
	private Long id;
	//节日开始时间
	private Calendar startTime;
	//节日结束时间
	private Calendar endTime;
	//columns END

	public Festival(){
	}

	public Festival(
		Long id
	){
		this.id = id;
	}

	/**
	 * id
	 * @return
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * id
	 * @param value
	 */
	public void setId(Long value) {
		this.id = value;
	}
	/**
	 * 节日开始时间
	 * @return
	 * @throws ParseException 
	 */
	public String getStartTimeString() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date2String = date2String(getStartTime(), DATE_TIME_FORMAT);
		Date parse = sdf.parse(date2String);
		String dateStr = sdf.format(parse);
		 //date2String(getStartTime(), DATE_TIME_FORMAT);
		return	dateStr;
	}
	
	/**
	 * 节日开始时间
	 * @param value
	 */
	public void setStartTimeString(String value) {
		setStartTime(string2Date(value, DATE_TIME_FORMAT));
	}
	
	/**
	 * 节日开始时间
	 * @return
	 */
	public Calendar getStartTime() {
		return this.startTime;
	}
	
	/**
	 * 节日开始时间
	 * @param value
	 */
	public void setStartTime(Calendar value) {
		this.startTime = value;
	}
	/**
	 * 节日结束时间
	 * @return
	 * @throws ParseException 
	 */
	public String getEndTimeString() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date2String = date2String(getEndTime(), DATE_TIME_FORMAT);
		Date parse = sdf.parse(date2String);
		String dateStr = sdf.format(parse);
		return dateStr;
	}
	
	/**
	 * 节日结束时间
	 * @param value
	 */
	public void setEndTimeString(String value) {
		setEndTime(string2Date(value, DATE_TIME_FORMAT));
	}
	
	/**
	 * 节日结束时间
	 * @return
	 */
	public Calendar getEndTime() {
		return this.endTime;
	}
	
	/**
	 * 节日结束时间
	 * @param value
	 */
	public void setEndTime(Calendar value) {
		this.endTime = value;
	}

}

