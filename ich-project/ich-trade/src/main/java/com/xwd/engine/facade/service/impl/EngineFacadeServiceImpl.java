package com.xwd.engine.facade.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.rpc.framework.core.proxy.ClassProxy;
import com.xwd.engine.facade.service.EngineFacadeService;
import com.xwd.engine.service.EngineService;
import com.xwd.engine.service.TradeService;

@Service
public class EngineFacadeServiceImpl implements EngineFacadeService, InitializingBean{

	private EngineService engineService;
	private TradeService tradeService;
	
	@Override
	public TradeService getTradeService() {
		if (tradeService == null) {
			tradeService = (TradeService) ClassProxy.create(TradeService.class);
		}
		return tradeService;
	}
	
	
	@Override
	public EngineService getEngineService() {
		if (engineService == null) {
			engineService = (EngineService) ClassProxy.create(EngineService.class);
		}
		return engineService;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		engineService = (EngineService) ClassProxy.create(EngineService.class);
		tradeService = (TradeService) ClassProxy.create(TradeService.class);
	}

}
