package com.xwd.customer.service.impl;

import com.xwd.base.util.RandomUtils;

import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;

import com.xwd.customer.model.*;
import com.xwd.customer.dao.*;
import com.xwd.customer.service.*;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class CustomerServiceImpl extends AbstractBaseService<Customer> implements CustomerService{

	@Autowired
	private CustomerDao customerDao;
	
	public EntityDao<Customer,Long> getEntityDao() {
		return this.customerDao;
	}
	
	@Override
	public int save(Customer entity) {
		return customerDao.save(entity);
	}

	@Override
	public int update(Customer entity) {
		return customerDao.update(entity);
	}

	@Override
	public int saveOrUpdate(Customer entity) {
		return customerDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Customer entity) {
		customerDao.delete(entity);
	}
	
	/**
	 * 根据手机号或者email获得用户
	 * @param params
	 * @return
	 */
	public Customer getCustomerByEmailOrMobile(Object ...params){
		return this.customerDao.getCustomerByEmailOrMobile(params);
	}

	public Boolean isUniqueTel(Object ...params){
		return this.customerDao.findBy(params).size()==0;
	}

	public Long genUniqeCustNo(String orgId){
		String numString=RandomUtils.generateNumString(8);
		Long custno=Long.valueOf(orgId+numString);
		if(this.customerDao.findBy("customerNo",custno).size()!=0){
			return genUniqeCustNo(orgId);
		}else{
			return custno;
		}


	}

	/**
	 * 查询最后一条数据
	 */
	public Customer findLastCustomer() {
		return customerDao.findLastCustomer();
	}
	
	
	/**
	 * 申购参与积分人数 
	 */
	public List<Customer> findPurchaseNum(int purchaseNum){
		return customerDao.finPurchaseNum(purchaseNum);
	}


	/**
	 * 获得前n名参与申购的成长总值
	 * @param paras
	 * @return
	 */
	public long getGrowthByPurchaseFront(Object... paras){
		return this.customerDao.getGrowthByPurchaseFront(paras);
	}

	@Override
	public List<Customer> findByPurchaseFront(Object... paras) {
		return this.customerDao.findByPurchaseFront(paras);
	}


}
