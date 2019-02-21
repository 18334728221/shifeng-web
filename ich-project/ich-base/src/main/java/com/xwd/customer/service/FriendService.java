package com.xwd.customer.service;

import com.frame.base.BaseService;
import com.xwd.customer.model.Friend;


public interface FriendService extends BaseService<Friend>{

	public int save(Friend entity);

	public int update(Friend entity);

	public int saveOrUpdate(Friend entity);

	public void delete(Friend entity);
}
