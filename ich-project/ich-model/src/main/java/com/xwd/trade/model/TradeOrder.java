package com.xwd.trade.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 交易订单
 */
public class TradeOrder extends BaseEntity {
		
	private static final long serialVersionUID = 2339091691776244443L;
	
	public final static Byte TRADE_MARK_BUY = 0;
	public final static Byte TRADE_MARK_SELL = 1;
	public final static Byte PRICE_METHOD_LIMIT = 0;//限价
	public final static Byte PRICE_METHOD_MARKET = 1;//市价
	public final static Byte PRICE_METHOD_AUCTION = 2;//竞价
	public final static Byte ENTRUST_METHOD_ONLINE = 0;//网上委托
	public final static Byte ENTRUST_METHOD_MOBILE = 1;//手机委托
	public final static Byte TRADE_STATUS_ORDER = 0;//委托下单
	public final static Byte TRADE_STATUS_ENTRUST = 1;//委托成功
	public final static Byte TRADE_STATUS_PART_TRADE = 2;//部分成交
	public final static Byte TRADE_STATUS_TRADE = 3;//已成交
	public final static Byte TRADE_STATUS_CANCEL = 4; //场内撤单
	public final static Byte TRADE_STATUS_CANCEL_OUTER = 5; //场外撤单
	
	public final static Byte CANCEL_STATUS_FAILURE = 0;
	public final static Byte CANCEL_STATUS_SUCCESS = 1;
	
	//columns START
	//id
	private Long id;
	//流水号 时间+委托编号
	private String txNo;
	//顾客编号
	private Long customerNo;
	//产品名称
	private String productName;
	//产品编号
	private Long productCode;
	//委托时间
	private Calendar entrustTime;
	//买卖标志 0:买入 1:卖出
	private Byte tradeMark;
	//买卖标志 0:买入 1:卖出
	private String tradeMarkString;
	//委托价格
	private BigDecimal entrustPrice;
	//委托数量
	private Long entrustNumber;
	//委托编号
	private Integer entrustCode;
	//成交数量
	private Long turnoverQuantity = 0L;
	//报价方式 0:限价 1:市价 2:竞价
	private Byte priceMethod;
	//报价方式 0:限价 1:市价 2:竞价
	private String priceMethodString;
	//委托方式 0:网上委托 1:手机委托
	private Byte entrustMethod;
	//委托方式 0:网上委托 1:手机委托
	private String entrustMethodString;
	//状态说明 0:委托 1:委托成功  2:部分成交 3:已成交 4:场内撤单
	private Byte tradeStatus = 0;
	//是否发起了撤单
	private Boolean isCancel = false;
	//撤单状态
	private Byte cancelStatus = 0;
	//撤单数量
	private Long cancelNumber = 0L;
	//手续费
	private BigDecimal poundage = new BigDecimal(0);
	//累计交易手续费,多单成交累计
	private BigDecimal accumulatedPoundage = new BigDecimal(0);
	//累计交易资金，不是每笔交易都是按委托价买入或者卖出
	private BigDecimal accumulatedFund = new BigDecimal(0);
	//总资金
	private BigDecimal totalFund = new BigDecimal(0);
	//多少资金来自可提现资金，撤单的时候返回
	private BigDecimal fromWithdrawCash = new BigDecimal(0);
	//股票卖出的冻结资金,隔天到账
	private BigDecimal stockSellFund = new BigDecimal(0);
	//是否交易时间
	private Boolean isTradeTime = false;
	//columns END
	
	//状态集合，获取最大的就是
	private List<Byte> tradeStatusList = new ArrayList<Byte>();
	//卖手续费率
	private BigDecimal sellRate = new BigDecimal(0.002);

	public TradeOrder(){
	}

	public TradeOrder(
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
	 * 顾客编号
	 * @return
	 */
	public Long getCustomerNo() {
		return this.customerNo;
	}
	
	/**
	 * 顾客编号
	 * @param value
	 */
	public void setCustomerNo(Long value) {
		this.customerNo = value;
	}
	/**
	 * 产品名称
	 * @return
	 */
	public String getProductName() {
		return this.productName;
	}
	
	/**
	 * 产品名称
	 * @param value
	 */
	public void setProductName(String value) {
		this.productName = value;
	}
	/**
	 * 产品编号
	 * @return
	 */
	public Long getProductCode() {
		return this.productCode;
	}
	
	/**
	 * 产品编号
	 * @param value
	 */
	public void setProductCode(Long value) {
		this.productCode = value;
	}
	/**
	 * 委托时间
	 * @return
	 */
	public String getEntrustTimeString() {
		return date2String(getEntrustTime(), DATE_TIME_FORMAT);
	}
	
	/**
	 * 委托时间
	 * @param value
	 */
	public void setEntrustTimeString(String value) {
		setEntrustTime(string2Date(value, DATE_TIME_FORMAT));
	}
	
	/**
	 * 委托时间
	 * @return
	 */
	public Calendar getEntrustTime() {
		return this.entrustTime;
	}
	
	/**
	 * 委托时间
	 * @param value
	 */
	public void setEntrustTime(Calendar value) {
		this.entrustTime = value;
	}
	/**
	 * 买卖标志 0:买入 1:卖出
	 * @return
	 */
	public Byte getTradeMark() {
		return this.tradeMark;
	}
	/**
	 * 买卖标志 0:买入 1:卖出
	 * @return
	 */
	public String getTradeMarkString() {
		String tradeMarkString = "";
		if(this.tradeMark!=null){
			if(this.tradeMark==0){
				tradeMarkString = "买入";
			}else{
				tradeMarkString = "卖出";
			}
		}
		return tradeMarkString;
	}
	
	/**
	 * 买卖标志 0:买入 1:卖出
	 * @param value
	 */
	public void setTradeMark(Byte value) {
		this.tradeMark = value;
	}
	/**
	 * 委托价格
	 * @return
	 */
	public BigDecimal getEntrustPrice() {
		return this.entrustPrice;
	}
	
	/**
	 * 委托价格
	 * @param value
	 */
	public void setEntrustPrice(BigDecimal value) {
		this.entrustPrice = value;
	}
	/**
	 * 委托数量
	 * @return
	 */
	public Long getEntrustNumber() {
		return this.entrustNumber;
	}
	
	/**
	 * 委托数量
	 * @param value
	 */
	public void setEntrustNumber(Long value) {
		this.entrustNumber = value;
	}
	/**
	 * 委托编号
	 * @return
	 */
	public Integer getEntrustCode() {
		return this.entrustCode;
	}
	
	/**
	 * 委托编号
	 * @param value
	 */
	public void setEntrustCode(Integer value) {
		this.entrustCode = value;
	}
	/**
	 * 成交数量
	 * @return
	 */
	public Long getTurnoverQuantity() {
		return this.turnoverQuantity;
	}
	
	/**
	 * 成交数量
	 * @param value
	 */
	public void setTurnoverQuantity(Long value) {
		this.turnoverQuantity = value;
	}
	
	public void addTurnoverQuantity(Long value) {
		this.turnoverQuantity += value;
	}
	
	/**
	 * 是否全部成交
	 * @return
	 */
	public boolean isAllTraded(){
		return this.turnoverQuantity == this.entrustNumber;
	}
	
	/**
	 * 报价方式 0:限价 1:竞价
	 * @return
	 */
	public Byte getPriceMethod() {
		return this.priceMethod;
	}
	
	/**
	 * 报价方式 0:限价 1:竞价
	 * @param value
	 */
	public void setPriceMethod(Byte value) {
		this.priceMethod = value;
	}
	
	//0:限价 1:市价 2:竞价
	public String getPriceMethodString() {
		String priceMethodString="";
		if(this.priceMethod!=null){
			if(this.priceMethod==0){
				priceMethodString = "限价";
			}else if(this.priceMethod==1){
				priceMethodString = "市价";
			}else{
				priceMethodString = "竞价";
			}
		}
		return priceMethodString;
	}
	
	//委托方式 0:网上委托 1:手机委托
	public String getEntrustMethodString() {
		String entrustMethodString="";
		if(this.entrustMethod!=null){
			if(this.entrustMethod==0){
				entrustMethodString = "网上委托";
			}else{
				entrustMethodString = "手机委托";
			}
		}
		return entrustMethodString;
	}
	/**
	 * 委托方式 0:网上委托 1:手机委托
	 * @return
	 */
	public Byte getEntrustMethod() {
		return this.entrustMethod;
	}
	
	/**
	 * 委托方式 0:网上委托 1:手机委托
	 * @param value
	 */
	public void setEntrustMethod(Byte value) {
		this.entrustMethod = value;
	}
	/**
	 * 状态说明 0:委托 1:委托成功  2:部分成交 3:已成交 4:场内撤单
	 * @return
	 */
	public Byte getTradeStatus() {
		if(this.tradeStatusList == null || this.tradeStatusList.isEmpty()){
			return this.tradeStatus;
		} else {
			Collections.sort(tradeStatusList);
			return this.tradeStatusList.get(tradeStatusList.size() - 1);
		}
	}
	
	/**
	 * 状态说明 0:委托 1:委托成功 2:已成交 3:部分成交 4:场内撤单
	 * @param value
	 */
	public void setTradeStatus(Byte value) {
		if(this.tradeStatus > value){
			return;
		}
		this.tradeStatus = value;
	}

	public String getTxNo() {
		return txNo;
	}

	public void setTxNo(String txNo) {
		this.txNo = txNo;
	}
	
	public BigDecimal getPoundage() {
		return poundage;
	}

	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
	}

	public BigDecimal getAccumulatedPoundage() {
		return accumulatedPoundage;
	}

	public void setAccumulatedPoundage(BigDecimal accumulatedPoundage) {
		this.accumulatedPoundage = accumulatedPoundage;
	}

	/**
	 * 每笔交易手续费累加
	 * @param poundage
	 */
	public void addPoundage(BigDecimal poundage){
		accumulatedPoundage = accumulatedPoundage.add(poundage);
	}
	
	public BigDecimal getAccumulatedFund() {
		return accumulatedFund;
	}

	public void setAccumulatedFund(BigDecimal accumulatedFund) {
		this.accumulatedFund = accumulatedFund;
	}
	
	public void addTradeFund(BigDecimal accumulatedFund) {
		this.accumulatedFund = this.accumulatedFund.add(accumulatedFund);
	}

	public Boolean getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(Boolean isCancel) {
		this.isCancel = isCancel;
	}

	public Byte getCancelStatus() {
		return cancelStatus;
	}

	public void setCancelStatus(Byte cancelStatus) {
		this.cancelStatus = cancelStatus;
	}

	public List<Byte> getTradeStatusList() {
		return tradeStatusList;
	}

	public void setTradeStatusList(List<Byte> tradeStatusList) {
		this.tradeStatusList = tradeStatusList;
	}
	
	public void addTradeStatus(Byte b){
		if(this.turnoverQuantity == this.entrustNumber){
			this.tradeStatusList.add(TRADE_STATUS_TRADE);
		} else {
			this.tradeStatusList.add(b);
		}
	}

	public Long getCancelNumber() {
		return cancelNumber;
	}

	public void setCancelNumber(Long cancelNumber) {
		this.cancelNumber = cancelNumber;
	}

	public BigDecimal getFromWithdrawCash() {
		return fromWithdrawCash;
	}

	public void setFromWithdrawCash(BigDecimal fromWithdrawCash) {
		this.fromWithdrawCash = fromWithdrawCash;
	}

	public BigDecimal getTotalFund() {
		return totalFund;
	}

	public void setTotalFund(BigDecimal totalFund) {
		this.totalFund = totalFund;
	}

	public BigDecimal getStockSellFund() {
		return stockSellFund;
	}

	public void setStockSellFund(BigDecimal stockSellFund) {
		this.stockSellFund = stockSellFund;
	}
	
	/**
	 * 获得可撤退资金
	 * totalFund - entrustPrice * turnoverQuantity - accumulatedPoundage
	 * @return
	 */
	public BigDecimal getFundCanBack(){
		BigDecimal result = this.getEntrustPrice().multiply(new BigDecimal(this.turnoverQuantity));
		result = this.totalFund.subtract(result);
		return result.subtract(accumulatedPoundage);
	}

	public Boolean getIsTradeTime() {
		return isTradeTime;
	}

	public void setIsTradeTime(Boolean isTradeTime) {
		this.isTradeTime = isTradeTime;
	}

	public BigDecimal getSellRate() {
		return sellRate;
	}

	public void setSellRate(BigDecimal sellRate) {
		this.sellRate = sellRate;
	}

}

