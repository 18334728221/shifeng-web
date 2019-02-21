package com.xwd.dd.model;

import java.util.Calendar;

import com.frame.base.BaseEntity;
import com.frame.util.CalendarUtils;

/**
 * 地区
 * @author sf
 */
public class Area extends BaseEntity {
	private static final long serialVersionUID = -4580584709886327450L;
	public final static byte CITY_LEVEL_FIRST = 1;
	//columns START
	//id
	private Long id;
	//地区名称
	private String areaName;
	//地区编码
	private String areaCode;
	//父Id
	private Long parentId;
	//区域标识，也就是平台标识
	private Long platMark;
	//地区级别
	private Byte level;
	//几线城市
	private Byte cityLevel;
	//大区域标识
	private Byte regional;
	//是否展开
	private Boolean isExpand;
	//columns END
	//父平台标识
	private Long parentPlatMark;

	public Area(){}

	public Area(Long id){
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
	 * 地区名称
	 * @return
	 */
	public String getAreaName() {
		return this.areaName;
	}
	
	/**
	 * 地区名称
	 * @param value
	 */
	public void setAreaName(String value) {
		this.areaName = value;
	}
	/**
	 * 地区编码
	 * @return
	 */
	public String getAreaCode() {
		return this.areaCode;
	}
	
	/**
	 * 地区编码
	 * @param value
	 */
	public void setAreaCode(String value) {
		this.areaCode = value;
	}
	/**
	 * parentId
	 * @return
	 */
	public Long getParentId() {
		return this.parentId;
	}
	
	/**
	 * parentId
	 * @param value
	 */
	public void setParentId(Long value) {
		this.parentId = value;
	}
	/**
	 * 区域标识，也就是平台标识
	 * @return
	 */
	public Long getPlatMark() {
		return this.platMark;
	}
	
	/**
	 * 区域标识，也就是平台标识
	 * @param value
	 */
	public void setPlatMark(Long value) {
		this.platMark = value;
	}
	/**
	 * 层
	 * @return
	 */
	public Byte getLevel() {
		return this.level;
	}
	
	/**
	 * 层
	 * @param value
	 */
	public void setLevel(Byte value) {
		this.level = value;
	}
	
	
	public Byte getCityLevel() {
		return cityLevel;
	}

	public void setCityLevel(Byte cityLevel) {
		this.cityLevel = cityLevel;
	}

	/**
	 * regional
	 * @return
	 */
	public Byte getRegional() {
		return this.regional;
	}
	
	/**
	 * regional
	 * @param value
	 */
	public void setRegional(Byte value) {
		this.regional = value;
	}
	/**
	 * isExpand
	 * @return
	 */
	public Boolean getIsExpand() {
		return this.isExpand;
	}
	
	/**
	 * isExpand
	 * @param value
	 */
	public void setIsExpand(Boolean value) {
		this.isExpand = value;
	}

	public Long getParentPlatMark() {
		return parentPlatMark;
	}

	public void setParentPlatMark(Long parentPlatMark) {
		this.parentPlatMark = parentPlatMark;
	}
	
	
}

