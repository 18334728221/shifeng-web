package com.xwd.customer.service;

import java.util.List;

import com.frame.base.BaseService;
import com.xwd.customer.model.Customer;


public interface CustomerService extends BaseService<Customer>{

	public int save(Customer entity);

	public int update(Customer entity);

	public int saveOrUpdate(Customer entity);

	public void delete(Customer entity);
	
	/**
	 * 根据手机号或者email获得用户
	 * @param params
	 * @return
	 */
	public Customer getCustomerByEmailOrMobile(Object ...params);

	/**
	 *检查是否手机号重复
	 * @param params
	 * @return
	 */
	public Boolean isUniqueTel(Object ...params);

	/**
	 *根据机构id生成独立的cusno
	 * @param orgId
	 * @return
	 */
	public Long genUniqeCustNo(String orgId);
	
	/**
	 * 查询最后一条数据
	 */
	public Customer findLastCustomer();
	/**
	 * 查询申购参与积分人数
	 */
	public List<Customer> findPurchaseNum(int purchaseNum);

	/**
	 * 获得前n名参与申购的成长总值
	 * @param paras
	 * @return
	 */
	public long getGrowthByPurchaseFront(Object... paras);
	
	/**
	 * 根据申购记录获得前n名成长值高的会员
	 * @param paras
	 * @return
	 */
	public List<Customer> findByPurchaseFront(Object... paras);
}
