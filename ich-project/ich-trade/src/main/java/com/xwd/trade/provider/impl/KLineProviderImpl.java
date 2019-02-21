package com.xwd.trade.provider.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.frame.util.CalendarUtils;
import com.xwd.engine.model.TradeRecord;
import com.xwd.securities.model.Stock;
import com.xwd.trade.dto.KLine;
import com.xwd.trade.dto.KLineTime;
import com.xwd.trade.provider.KLineProvider;

/**
 * K线处理类
 * 
 * @author ljl
 *
 */
@Service
public class KLineProviderImpl implements KLineProvider {
	
	private final static String KLINE_TYPE_KEY = "kline_type";
	
	@Autowired
	private RedisTemplate tradeRedisTemplate;

	@Override
	public void create(Stock stock, KLineTime time) {
		if (stock == null || time == null) {
			return;
		}
		// 分钟线
		// 处理1分钟
		if (!create(stock, time, 1, KLine.ONE_MINUTE_K_LINE)) {
			return;
		}
		// 5分钟线
		if (!create(stock, time, 5, KLine.FIVE_MINUTE_K_LINE)) {
			return;
		}
		// 15分钟线
		if (!create(stock, time, 15, KLine.FIFTEEN_MINUTE_K_LINE)) {
			return;
		}
		// 30分钟线
		if (!create(stock, time, 30, KLine.THIRTY_MINUTE_K_LINE)) {
			return;
		}
		// 60分钟线
		if (!create(stock, time, 60, KLine.SIXTY_MINUTE_K_LINE)) {
			return;
		}
		// 日线
		if (!create(stock, time, KLine.KLINE_DAY_TYPE, KLine.DAY_K_LINE)) {
			return;
		}
		// 周线
		create(stock, time, KLine.KLINE_WEEK_TYPE, KLine.WEEK_K_LINE);
		// 月线
		create(stock, time, KLine.KLINE_MONTH_TYPE, KLine.MONTH_K_LINE);
		// 季线
		create(stock, time, KLine.KLINE_QUARTER_TYPE, KLine.QUARTER_K_LINE);
		// 年线
		create(stock, time, KLine.KLINE_YEAR_TYPE, KLine.YEAR_K_LINE);
	}

	private boolean create(Stock stock, KLineTime time, int kLineType, String redisKey) {
		KLine entity = new KLine();
		//开盘的时候初始化是昨天的收盘价,今天开盘价初始化位0
		if(BigDecimal.ZERO.equals(stock.getPrice())){
			entity.setClosingPrice(stock.getClosingPrice());
			entity.setHighestPrice(stock.getClosingPrice());
			entity.setLowestPrice(stock.getClosingPrice());
		} else {
			entity.setClosingPrice(stock.getPrice());
			entity.setHighestPrice(stock.getPrice());
			entity.setLowestPrice(stock.getPrice());
		}
		entity.setProductCode(stock.getCode());
		entity.setTradeTime(time.getTradeTime());
		entity.setTimestamps(time.getDatetime().getTimeInMillis());
		entity.setType(kLineType);
		String key = this.getKey(kLineType, time);
		//hash存储key
		String skey = redisKey + stock.getCode();
		//分钟线处理 日线处理
		if(kLineType <= KLine.KLINE_DAY_TYPE){
			if(tradeRedisTemplate.opsForHash().hasKey(skey, key)){
				return false;
			} else {
				tradeRedisTemplate.opsForHash().put(skey, key, entity);
				//保存对应K先最新标key
				tradeRedisTemplate.opsForValue().set(KLINE_TYPE_KEY + skey, key);
			}
		} else {//周 月 季 年线处理
			if(!tradeRedisTemplate.opsForHash().hasKey(skey, key)){
				tradeRedisTemplate.opsForHash().put(skey, key, entity);
				tradeRedisTemplate.opsForValue().set(KLINE_TYPE_KEY + skey, key);
			}
		}
		return true;
	}
	
	

	@Override
	public List<KLine> find(Long code, String kLineType) {
		Set<Object> set = tradeRedisTemplate.opsForHash().keys(kLineType + code);
		List<Object> list = tradeRedisTemplate.opsForHash().multiGet(kLineType + code, set);
		List<KLine> result = new ArrayList<KLine>();
		for(Object o : list){
			result.add((KLine)o);
		}
		return result;
	}

	@Override
	public Map<String, List<KLine>> findKLines(Long code) {
		Map<String, List<KLine>> map = new HashMap<String, List<KLine>>();
		for (String str : KLine.K_LINE_MAP.values()) {
			map.put(str, this.find(code, str));
		}
		return map;
	}
	
	@Override
	public KLine get(Long code, String kLineType){
		String key = (String)tradeRedisTemplate.opsForValue().get(KLINE_TYPE_KEY + kLineType + code);
		return (KLine)tradeRedisTemplate.opsForHash().get(kLineType + code, key);
	}

	@Override
	public void update(Long circulatingStock, TradeRecord tr) {
		try{
			KLineTime time = new KLineTime();
			time.setBeginTradeTime(tr.getBeginTradeTime());
			time.setTradeTime(tr.getTradeTime());
			time.setDatetime(CalendarUtils.convertCalendarOne(time.getTradeTime()));
			KLine entity;
			String key;
			for(Entry<Integer, String> entry : KLine.K_LINE_MAP.entrySet()){
				key = this.getKey(entry.getKey(), time);
				entity = (KLine)tradeRedisTemplate.opsForHash().get(entry.getValue() + tr.getCode(), key);
				if(entity == null){
					Thread.currentThread().sleep(50);
					entity = (KLine)tradeRedisTemplate.opsForHash().get(entry.getValue() + tr.getCode(), key);
					if(entity == null){
						continue;
					}
				}
				//存在更新信息
				this.calculateIndex(entity, circulatingStock, tr);
				tradeRedisTemplate.opsForHash().put(entry.getValue() + tr.getCode(), key, entity);
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据K线类型获得时间key
	 * @param type
	 * @param time
	 * @return
	 */
	private String getKey(int type, KLineTime time){
		if(type == 1){
			return time.getAsMinute();
		} else if(type < KLine.KLINE_DAY_TYPE){
			return time.getAsMinuteFromBeginTime(type);
		} else if(type == KLine.KLINE_DAY_TYPE){
			return time.getDay();
		} else if(type == KLine.KLINE_WEEK_TYPE){
			return time.getWeekFirstDay();
		} else if(type == KLine.KLINE_MONTH_TYPE){
			return time.getFirstDayOfMonth();
		} else if(type == KLine.KLINE_QUARTER_TYPE){
			return time.getFirstDayOfQuarter();
		} else if(type == KLine.KLINE_YEAR_TYPE){
			return time.getFirstDayOfYear();
		}
		return "";
	}
	
	/**
	 * 买入交易成功计算股票K线各项指标
	 * @param stock
	 * @param obj
	 */
	private void calculateIndex(KLine kLine, Long circulatingStock, TradeRecord obj){
		//最新价格
		kLine.setPrice(obj.getLatestPrice());
		//最高价
		if(kLine.getHighestPrice().compareTo(obj.getLatestPrice()) < 0){
			kLine.setHighestPrice(obj.getLatestPrice());
		}
		//最低价
		if(kLine.getLowestPrice().compareTo(obj.getLatestPrice()) < 0){
			kLine.setLowestPrice(obj.getLatestPrice());
		}
		//成交量
		kLine.setVolume(kLine.getVolume() + obj.getVolume() * 2);
		//成交总金额
		kLine.setTotalAmount(kLine.getTotalAmount().add(obj.getTotalAmount()));
		//涨跌幅
		BigDecimal a = kLine.getPrice().subtract(kLine.getClosingPrice());
		BigDecimal b = a.divide(kLine.getClosingPrice(), 4, BigDecimal.ROUND_CEILING);
		b = b.multiply(new BigDecimal(100));
		kLine.setPriceFluctuation(b.floatValue());
		//涨跌价
		kLine.setChangePrice(kLine.getHighestPrice().subtract(kLine.getClosingPrice()));
		//换手率
		a = new BigDecimal(kLine.getVolume());
		b = new BigDecimal(circulatingStock);
		b = a.divide(kLine.getClosingPrice(), 4, BigDecimal.ROUND_CEILING);
		b = b.multiply(new BigDecimal(100));
		kLine.setTurnoverRate(b.floatValue());
	}
}
