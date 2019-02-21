package com.xwd.trade.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ljl
 * K线
 */
public class KLine implements Serializable {
		
	private static final long serialVersionUID = -6935453836333602853L;
	
	public final static String ONE_MINUTE_K_LINE = "kline_one";
	public final static String FIVE_MINUTE_K_LINE = "kline_five";
	public final static String FIFTEEN_MINUTE_K_LINE = "kline_fifteen";
	public final static String THIRTY_MINUTE_K_LINE = "kline_thirty";
	public final static String SIXTY_MINUTE_K_LINE = "kline_sixty";
	public final static String DAY_K_LINE = "kline_day";
	public final static String WEEK_K_LINE = "kline_week";
	public final static String MONTH_K_LINE = "kline_month";
	public final static String QUARTER_K_LINE = "kline_quarter";
	public final static String YEAR_K_LINE = "kline_year";
	
	public final static int KLINE_DAY_TYPE = 61;
	public final static int KLINE_WEEK_TYPE = 62;
	public final static int KLINE_MONTH_TYPE = 63;
	public final static int KLINE_QUARTER_TYPE = 64;
	public final static int KLINE_YEAR_TYPE = 65;
	
	public final static Map<Integer, String> K_LINE_MAP = new HashMap<Integer, String>();
	static{
		K_LINE_MAP.put(1, ONE_MINUTE_K_LINE);
		K_LINE_MAP.put(5, FIVE_MINUTE_K_LINE);
		K_LINE_MAP.put(15, FIFTEEN_MINUTE_K_LINE);
		K_LINE_MAP.put(30, THIRTY_MINUTE_K_LINE);
		K_LINE_MAP.put(60, SIXTY_MINUTE_K_LINE);
		K_LINE_MAP.put(61, DAY_K_LINE);
		K_LINE_MAP.put(62, WEEK_K_LINE);
		K_LINE_MAP.put(63, MONTH_K_LINE);
		K_LINE_MAP.put(64, QUARTER_K_LINE);
		K_LINE_MAP.put(65, YEAR_K_LINE);
	}
	
	//当前价
	private BigDecimal price = new BigDecimal(0);
	//产品编号
	private Long productCode;
	//开盘价
	private BigDecimal openingPrice = new BigDecimal(0);
	//最新价
	private BigDecimal closingPrice = new BigDecimal(0);
	//最高价
	private BigDecimal highestPrice = new BigDecimal(0);
	//最低价
	private BigDecimal lowestPrice = new BigDecimal(0);
	//交易时间 2017-07-29
	private String tradeTime;
	//成交总金额
	private BigDecimal totalAmount = new BigDecimal(0);
	//成交量
	private Long volume = 0L;
	//涨跌幅
	private Float priceFluctuation = 0f;
	//换手率
	private Float turnoverRate = 0f;
	//涨跌价格
	private BigDecimal changePrice = new BigDecimal(0);
	//columns END

	//时间2017-07-29 19:12格式表示
	private String key;
	private Long timestamps;
	private int type = 0;//k线类型
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
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
	 * 开盘价
	 * @return
	 */
	public BigDecimal getOpeningPrice() {
		return this.openingPrice;
	}
	
	/**
	 * 开盘价
	 * @param value
	 */
	public void setOpeningPrice(BigDecimal value) {
		this.openingPrice = value;
	}
	/**
	 * 收盘价
	 * @return
	 */
	public BigDecimal getClosingPrice() {
		return this.closingPrice;
	}
	
	/**
	 * 收盘价
	 * @param value
	 */
	public void setClosingPrice(BigDecimal value) {
		this.closingPrice = value;
	}
	/**
	 * 最高价
	 * @return
	 */
	public BigDecimal getHighestPrice() {
		return this.highestPrice;
	}
	
	/**
	 * 最高价
	 * @param value
	 */
	public void setHighestPrice(BigDecimal value) {
		this.highestPrice = value;
	}
	/**
	 * 最低价
	 * @return
	 */
	public BigDecimal getLowestPrice() {
		return this.lowestPrice;
	}
	
	/**
	 * 最低价
	 * @param value
	 */
	public void setLowestPrice(BigDecimal value) {
		this.lowestPrice = value;
	}
	/**
	 * 交易时间
	 * @return
	 */
	public String getTradeTime() {
		return this.tradeTime;
	}
	
	/**
	 * 交易时间
	 * @param value
	 */
	public void setTradeTime(String value) {
		this.tradeTime = value;
	}
	/**
	 * 成交总金额
	 * @return
	 */
	public BigDecimal getTotalAmount() {
		return this.totalAmount;
	}
	
	/**
	 * 成交总金额
	 * @param value
	 */
	public void setTotalAmount(BigDecimal value) {
		this.totalAmount = value;
	}
	/**
	 * 成交量
	 * @return
	 */
	public Long getVolume() {
		return this.volume;
	}
	
	/**
	 * 成交量
	 * @param value
	 */
	public void setVolume(Long value) {
		this.volume = value;
	}
	/**
	 * 涨跌幅
	 * @return
	 */
	public Float getPriceFluctuation() {
		return this.priceFluctuation;
	}
	
	/**
	 * 涨跌幅
	 * @param value
	 */
	public void setPriceFluctuation(Float value) {
		this.priceFluctuation = value;
	}
	/**
	 * 换手率
	 * @return
	 */
	public Float getTurnoverRate() {
		return this.turnoverRate;
	}
	
	/**
	 * 换手率
	 * @param value
	 */
	public void setTurnoverRate(Float value) {
		this.turnoverRate = value;
	}
	
	/**
	 * 涨跌价格
	 * @return
	 */
	public BigDecimal getChangePrice() {
		return this.changePrice;
	}
	
	/**
	 * 涨跌价格
	 * @param value
	 */
	public void setChangePrice(BigDecimal value) {
		this.changePrice = value;
	}

	public String getKey() {
		return key;
	}
	public String getTime(){return key;}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getTimestamps() {
		return timestamps;
	}

	public void setTimestamps(Long timestamps) {
		this.timestamps = timestamps;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}

