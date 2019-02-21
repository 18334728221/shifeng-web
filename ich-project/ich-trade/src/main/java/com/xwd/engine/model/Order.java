package com.xwd.engine.model;

import java.io.Serializable;

/**
 * 交易订单
 * 
 * @author ljl
 *
 */
public class Order implements Serializable {

	private static final long serialVersionUID = -6770259666602084852L;

	public final static Byte TRADE_MARK_BUY = 0;
	public final static Byte TRADE_MARK_SELL = 1;
	public final static Byte TRADE_MARK_CANCEL = 2;// 撤单
	
	public final static Byte PRICE_METHOD_LIMIT = 0;// 限价
	public final static Byte PRICE_METHOD_MARKET = 1;// 市价
	public final static Byte PRICE_METHOD_AUCTION = 2;// 竞价
	
	
	public final static Byte ORDER_STATUS_IDLE = 0;//空闲
	public final static Byte ORDER_STATUS_CACLUATE = 1;//计算中
	public final static Byte ORDER_STATUS_CANCEL = 2;//撤单中
	
	//券商ID
	private String broker = "";
	//股东号码
	private Long shareholderNo;
	private String txNo;
	private Long code;
	private Long price;
	private volatile Long volume;
	// 买卖标志 0:买入 1:卖出
	private Byte tradeMark;
	// 报价方式 0:限价 1:市价 2:竞价
	private Byte priceMethod;
	//辅助字段 排序用
	private Double score;
	//对应的对象状态位
	private Byte status = 0;
	
	public String getBroker() {
		return broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
	}
	
	public Long getShareholderNo() {
		return shareholderNo;
	}

	public void setShareholderNo(Long shareholderNo) {
		this.shareholderNo = shareholderNo;
	}

	public String getTxNo() {
		return txNo;
	}

	public void setTxNo(String txNo) {
		this.txNo = txNo;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getVolume() {
		return volume;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}

	public Byte getTradeMark() {
		return tradeMark;
	}

	public void setTradeMark(Byte tradeMark) {
		this.tradeMark = tradeMark;
	}

	public Byte getPriceMethod() {
		return priceMethod;
	}

	public void setPriceMethod(Byte priceMethod) {
		this.priceMethod = priceMethod;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}
	
	public String getKey(){
		return this.getCode() + "" + this.tradeMark;
	}
	
	public String getOppositeKey(){
		if(this.tradeMark == TRADE_MARK_BUY){
			return this.getCode() + "" + TRADE_MARK_SELL;
		} else {
			return this.getCode() + "" + TRADE_MARK_BUY;
		}
	}
	
	public void substract(Long volume){
		this.volume = this.volume - volume;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

}
