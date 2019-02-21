package com.xwd.customer.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.customer.model.Customer;


@Component
public class CustomerDao extends BaseMyIbatisDao<Customer, Long> {

	public Class<Customer> getEntityClass() {
		return Customer.class;
	}
	
	public int saveOrUpdate(Customer entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<Customer> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	/**
	 * 根据手机号或者email获得用户
	 * @param params
	 * @return
	 */
	public Customer getCustomerByEmailOrMobile(Object ...params){
		return db().selectOne("Customer.getCustomerByEmailOrMobile", map(params));
	}
	
	/**
	 * 查询最后一条数据
	 */
	public Customer findLastCustomer(){
		return db().selectOne("Customer.findLastCustomer");
	}
	
	/**
	 * 查询申购参与积分人数 
	 */
	public List<Customer> finPurchaseNum(int purchaseNum){
		return db().selectList("Customer.findPurchaseNum",purchaseNum);
	}
	

	/**
	 * 获得前n名参与申购的成长总值
	 * @param paras
	 * @return
	 */
	public long getGrowthByPurchaseFront(Object... paras){
		return db().selectOne(getEntityClass().getSimpleName() + ".getGrowthByPurchaseFront", map(paras));
	}
	
	/**
	 * 根据申购记录获得前n名成长值高的会员
	 * @param paras
	 * @return
	 */
	public List<Customer> findByPurchaseFront(Object... paras){
		return db().selectList(getEntityClass().getSimpleName() + ".findByPurchaseFront", map(paras));
	}
}
