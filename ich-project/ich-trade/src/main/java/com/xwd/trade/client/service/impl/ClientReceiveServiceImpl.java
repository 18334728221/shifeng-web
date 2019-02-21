package com.xwd.trade.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rpc.framework.core.service.ClientReceiveService;
import com.xwd.engine.model.TradeRecord;
import com.xwd.trade.provider.TradeRecordProvider;

/**
 * 交易记录接收处理
 * @author ljl
 *
 */
@Service
public class ClientReceiveServiceImpl implements ClientReceiveService{

	@Autowired
	private TradeRecordProvider tradeRecordProvider;
	
	@Override
	public void receive(Object o) throws Exception {
		if(o instanceof TradeRecord){
			tradeRecordProvider.push((TradeRecord)o);
		}
	}

}
