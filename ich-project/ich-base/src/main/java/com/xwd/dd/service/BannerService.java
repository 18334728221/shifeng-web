package com.xwd.dd.service;

import com.frame.base.BaseService;
import com.xwd.base.model.Banner;


public interface BannerService extends BaseService<Banner>{

	public int save(Banner entity);

	public int update(Banner entity);

	public int saveOrUpdate(Banner entity);

	public void delete(Banner entity);
	
}
