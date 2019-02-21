package com.xwd.bean;

import java.util.ArrayList;
import java.util.List;

public class GridDataModel {

	//页面传来参数，原样返回
	private String sEcho;
	
	//数据库表总记录数
	private int iTotalRecords = 0 ;
	
	//查询之后符合条件的总数
	private int iTotalDisplayRecords = 0 ;
	
	//页面展示数据
	private List aaData;
	
	//列
	private List colmodel;

	public GridDataModel(){
		aaData = new ArrayList();
		colmodel = new ArrayList();
	}

	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public int getiTotalRecords() {
		return iTotalRecords;
	}
	
	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public List getColmodel(){
		return colmodel;
	}

	public void setColmodel(List list){
		colmodel = list;
	}

	public List getAaData(){
		return aaData;
	}

	public void setAaData(List list){
		aaData = list;
	}
}
