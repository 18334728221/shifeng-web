package com.xwd.base.service;

import com.frame.base.BaseService;
import com.xwd.base.model.ImageServer;


public interface ImageServerService extends BaseService<ImageServer>{

	public int save(ImageServer entity);

	public int update(ImageServer entity);

	public int saveOrUpdate(ImageServer entity);

	public void delete(ImageServer entity);
	
	public void initCache();
}
