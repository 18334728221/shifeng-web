package com.xwd.base.service.impl;

import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.base.dao.ImageServerDao;
import com.xwd.base.model.ImageServer;
import com.xwd.base.provider.ImageServerProvider;
import com.xwd.base.service.ImageServerService;

/**
 * @author ljl
 */
@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class ImageServerServiceImpl extends AbstractBaseService<ImageServer> implements ImageServerService{

	@Autowired
	private ImageServerDao imageServerDao;
	@Autowired
	private ImageServerProvider imageServerProvider;
	
	public EntityDao<ImageServer,Long> getEntityDao() {
		return this.imageServerDao;
	}
	
	@Override
	public int save(ImageServer entity) {
		int n = imageServerDao.save(entity);
		imageServerProvider.saveOrUpdate(entity);
		return n;
	}

	@Override
	public int update(ImageServer entity) {
		int n = imageServerDao.update(entity);
		imageServerProvider.saveOrUpdate(entity);
		return n;
	}

	@Override
	public int saveOrUpdate(ImageServer entity) {
		int n = imageServerDao.saveOrUpdate(entity);
		imageServerProvider.saveOrUpdate(entity);
		return n;
	}

	@Override
	public void delete(ImageServer entity) {
		imageServerDao.delete(entity);
		imageServerProvider.delete(entity.getId());
	}

	@Override
	public void initCache() {
		List<ImageServer> list = this.imageServerDao.findAll();
		for(ImageServer entity : list){
			imageServerProvider.saveOrUpdate(entity);
		}
	}
	
}
