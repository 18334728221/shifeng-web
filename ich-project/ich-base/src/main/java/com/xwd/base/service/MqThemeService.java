package com.xwd.base.service;

import com.frame.base.BaseService;
import com.xwd.base.model.MqTheme;

public interface MqThemeService extends BaseService<MqTheme>{

	public int save(MqTheme entity);

	public int update(MqTheme entity);

	public int saveOrUpdate(MqTheme entity);

	public void delete(MqTheme entity);
}
