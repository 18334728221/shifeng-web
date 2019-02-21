package com.xwd.mall.service;

import com.frame.base.BaseService;
import com.xwd.mall.model.Invoice;


public interface InvoiceService extends BaseService<Invoice>{

	public int save(Invoice entity);

	public int update(Invoice entity);

	public int saveOrUpdate(Invoice entity);

	public void delete(Invoice entity);
}
