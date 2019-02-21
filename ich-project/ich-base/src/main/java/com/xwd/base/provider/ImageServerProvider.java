package com.xwd.base.provider;

import java.util.List;

import com.xwd.base.model.ImageServer;

public interface ImageServerProvider {

	public void saveOrUpdate(ImageServer entity);
	
	public void delete(Long id);
	
	public ImageServer get(Long id);
	
	public ImageServer getByType(Byte serverType);
	
	public List<ImageServer> findAll();
}
