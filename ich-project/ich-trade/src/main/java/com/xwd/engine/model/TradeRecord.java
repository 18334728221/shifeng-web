package com.xwd.engine.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 成交记录
 * 
 * @author ljl
 *
 */
public class TradeRecord implements Serializable {

	private static final long serialVersionUID = 9132717483300558780L;
	public final static Byte TRADE_RECORD_STATUS_ENTRUST_SUCCESS = 1;
	public final static Byte TRADE_RECORD_STATUS_BUY = 2;
	public final static Byte TRADE_RECORD_STATUS_SELL = 3;
	public final static Byte TRADE_RECORD_STATUS_CANCEL = 4;
	
	//交易流水号
	private String txNo;
	//券商ID
	private String broker;
	//买入股东号码
	private Long buyShareholderNo;
	//卖出股东号码
	private Long sellShareholderNo;
	//股票编号
	private Long code;
	//买入单号
	private String buyerTxNo;
	//卖出单号
	private String sellerTxNo;
	//成交价格
	private Long price;
	//成交数量
	private Long volume = 0L;
	//开始交易时间
	private String beginTradeTime;
	//交易时间
	private long tradeTime = System.currentTimeMillis();
	//状态
	private Byte status;
	//是否内盘成交
	private Boolean isInner = false;
	//是否成功
	private Boolean isSuccess = true;
	//是否发送
	private Boolean isSend = false;
	//错误原因
	private String reason;
	
	public String getTxNo() {
		return txNo;
	}

	public void setTxNo(String txNo) {
		this.txNo = txNo;
	}

	public String getBroker() {
		return broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
	}

	public Long getBuyShareholderNo() {
		return buyShareholderNo;
	}

	public void setBuyShareholderNo(Long buyShareholderNo) {
		this.buyShareholderNo = buyShareholderNo;
	}

	public Long getSellShareholderNo() {
		return sellShareholderNo;
	}

	public void setSellShareholderNo(Long sellShareholderNo) {
		this.sellShareholderNo = sellShareholderNo;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getBuyerTxNo() {
		return buyerTxNo;
	}

	public void setBuyerTxNo(String buyerTxNo) {
		this.buyerTxNo = buyerTxNo;
	}

	public String getSellerTxNo() {
		return sellerTxNo;
	}

	public void setSellerTxNo(String sellerTxNo) {
		this.sellerTxNo = sellerTxNo;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getVolume() {
		if(volume == null){
			return 0L;
		}
		return volume;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Boolean getIsInner() {
		return isInner;
	}

	public void setIsInner(Boolean isInner) {
		this.isInner = isInner;
	}
	
	/**
	 * 获得最新价
	 * @return
	 */
	public BigDecimal getLatestPrice(){
		return new BigDecimal(price).divide(new BigDecimal(100));
	}

	/**
	 * 获得成交总金额
	 * @return
	 */
	public BigDecimal getTotalAmount(){
		return new BigDecimal(price).multiply(new BigDecimal(volume));
	}

	public Boolean isSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	/**
	 * 开始交易时间
	 * @return
	 */
	public String getBeginTradeTime() {
		return beginTradeTime;
	}

	public void setBeginTradeTime(String beginTradeTime) {
		this.beginTradeTime = beginTradeTime;
	}

	/**
	 * 返回交易时间
	 * @return
	 */
	public long getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(long tradeTime) {
		this.tradeTime = tradeTime;
	}

	public Boolean getIsSend() {
		return isSend;
	}

	public void setIsSend(Boolean isSend) {
		this.isSend = isSend;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
