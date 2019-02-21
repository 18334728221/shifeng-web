package com.xwd.base.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.base.dao.SysConfigDao;
import com.xwd.base.model.SysConfig;
import com.xwd.base.provider.SysConfigProvider;
import com.xwd.base.service.SysConfigService;



/**
 * @author sf
 */
@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class SysConfigServiceImpl extends AbstractBaseService<SysConfig> implements SysConfigService{

	@Autowired
	private SysConfigDao sysConfigDao;
	@Autowired
	private SysConfigProvider sysConfigProvider;
	
	public EntityDao<SysConfig,Long> getEntityDao() {
		return this.sysConfigDao;
	}
	
	@Override
	public int save(SysConfig entity) {
		return sysConfigDao.save(entity);
	}

	@Override
	public int update(SysConfig entity) {
		return sysConfigDao.update(entity);
	}

	@Override
	public int saveOrUpdate(SysConfig entity) {
		sysConfigProvider.saveOrUpdate(entity);
		return sysConfigDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(SysConfig entity) {
		sysConfigDao.delete(entity);
	}
	
	@Override
	public void deleteByIds(String ids){
		super.deleteByIds(ids);
		if(StringUtils.isNotBlank(ids)){
			String [] id = ids.split(",");
			for (String string : id) {
				sysConfigProvider.delete(Long.valueOf(string));
			}
		}
	}

	@Override
	public String getValue(String key) {
		SysConfig entity = this.sysConfigProvider.get(key);
		if(entity == null){
			entity = this.findUniqueBy("configKey", key);
		}
		if(entity == null){
			return null;
		}
		return entity.getConfigValue();
	}

	@Override
	public void initCache() {
		List<SysConfig> list = this.findAll();
		for(SysConfig entity : list){
			this.sysConfigProvider.saveOrUpdate(entity);
		}
	}
	
}
