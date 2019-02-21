package com.xwd.trade.provider;

import java.util.List;
import java.util.Map;

import com.xwd.engine.model.TradeRecord;
import com.xwd.securities.model.Stock;
import com.xwd.trade.dto.KLine;
import com.xwd.trade.dto.KLineTime;

/**
 * K线接口
 * 1分钟K线
 * 5分钟K线
 * 15分钟K线
 * 30分钟K线
 * 60分钟K线
 * @author ljl
 *
 */
public interface KLineProvider {
	
	public void create(Stock stock, KLineTime time);
	
	/**
	 * 获得K线
	 * @param code
	 * @param kLineType 对应KLine里面的静态变量值
	 * @return
	 */
	public List<KLine> find(Long code, String kLineType);
	
	/**
	 * 根据产品编号获得K线
	 * @param code 产品编号
	 * @return key=kLineType对应KLine里面的静态变量值
	 * 		   value = 500个对应K线集合
	 */
	public Map<String,List<KLine>> findKLines(Long code);
	
	/**
	 * 获得最新的一个K线
	 * @param code
	 * @param kLineType 对应KLine里面的静态变量值
	 * @return
	 */
	public KLine get(Long code, String kLineType);
	
	/**
	 * 根据交易记录更新K线
	 * @param circulatingStock 流通盘总量
	 * @param tr 最新交易记录
	 */
	public void update(Long circulatingStock, TradeRecord tr);
}
