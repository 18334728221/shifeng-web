package com.xwd.engine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 挂卖单
 * 
 * @author ljl
 *
 */
public class MarketOrder implements Serializable {

	private static final long serialVersionUID = 521346988742339996L;

	// 买单 降序
	private List<BuyBill> buyers = new ArrayList<BuyBill>();
	// 卖单 升序
	private List<SellBill> sellers = new ArrayList<SellBill>();

	public List<BuyBill> getBuyers() {
		return buyers;
	}

	public void setBuyers(List<BuyBill> buyers) {
		this.buyers = buyers;
	}

	public List<SellBill> getSellers() {
		return sellers;
	}

	public void setSellers(List<SellBill> sellers) {
		this.sellers = sellers;
	}
	
	public void add(BuyBill entity){
		buyers.add(entity);
	}
	
	public void add(SellBill entity){
		sellers.add(entity);
	}
	
	public void print(){
		System.out.println("------------------------------");
		for(BuyBill bb : buyers){
			System.out.println("买:" + bb.getPrice() + "   数量:" + bb.getAmount());
		}
		for(SellBill sb : sellers){
			System.out.println("卖:" + sb.getPrice() + "   数量:" + sb.getAmount());
		}
	}

}
