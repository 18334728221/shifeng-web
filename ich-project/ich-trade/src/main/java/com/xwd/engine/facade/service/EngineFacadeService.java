package com.xwd.engine.facade.service;

import com.xwd.engine.service.EngineService;
import com.xwd.engine.service.TradeService;

public interface EngineFacadeService {
	
	public EngineService getEngineService();
	
	public TradeService getTradeService();
}
