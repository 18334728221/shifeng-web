package com.xwd.engine.service;

import com.xwd.engine.model.TradeConfig;

public interface EngineService {
	
	/**
	 * 初始化交易信息
	 * @param config
	 */
	public void init(TradeConfig config);
	
	/**
	 * 启动
	 */
	public void start();
	
	/**
	 * 停止
	 */
	public void stop();
	
	
}
