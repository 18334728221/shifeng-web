package com.xwd.engine.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 卖单
 * @author ljl
 *
 */
public class SellBill implements Serializable, Comparable<SellBill>{

	private static final long serialVersionUID = 6142982695077233139L;
	
	private Long code;
	private Long price;
	private Long amount;
	
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
	
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
	public String getPriceStr(){
		if(price == null){
			return "";
		}
		DecimalFormat df = new DecimalFormat("#0.00");
		BigDecimal b = new BigDecimal(price);
		b = b.divide(new BigDecimal(100));
		return df.format(b);
	}
	
	@Override
	public int compareTo(SellBill o) {
		if(this.price > o.getPrice()){
			return 1;
		} else if(this.price < o.getPrice()){
			return -1;
		}
		return 0;
	}
	
}
