package com.nnk.springboot.service;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repository.TradeRepository;

@Service
public class TradeService {
	
	Logger logger = org.slf4j.LoggerFactory.getLogger(TradeService.class);

	
	@Autowired
	private TradeRepository tradeRepository;
	
	/**
	 * 
	 * @param trade
	 * @return new Trade
	 * @throws Exception
	 */
	public Trade add(Trade trade) throws Exception {
		if(trade.getId() == null) {
			logger.info("Trade saved successfully");
			return tradeRepository.save(trade);
		}else {
			logger.error("Trade not saved");
			throw new Exception();
		}
	}
	
	/**
	 * 
	 * @param trade
	 * @return Trade updated
	 * @throws Exception
	 */
	public Trade update(Trade trade) throws Exception {
		Trade tradeDb = tradeRepository.findById(trade.getId()).orElse(null);
		if(tradeDb != null) {
			tradeDb.setAccount(trade.getAccount());
			tradeDb.setBuyQuantity(trade.getBuyQuantity());
			tradeDb.setType(trade.getType());
			tradeRepository.save(tradeDb);
			logger.info("Rule update successfully");
			return tradeDb;
		}else {
			logger.error("Trade not updated");
			throw new Exception();
		}		
	}
	
	/**
	 * 
	 * @param trade
	 * @throws Exception
	 */
	public void delete(Trade trade) throws Exception {
		if(trade.getId() != null) {
			tradeRepository.delete(trade);
			logger.info("Trade has deleted successfully");
		}else {
			logger.error("Trade hasn't deleted");
			throw new Exception();
		}
		
	}
	
	/**
	 * 
	 * @return List of Trade
	 */
	public List<Trade> getList() {
		return tradeRepository.findAll();
		
		
	}
	
	/**
	 * 
	 * @param id
	 * @return Trade find by id if exist
	 * @throws Exception
	 */
	public Trade getById(Integer id) throws Exception {
		Trade trade = tradeRepository.findById(id).orElse(null);
		if (trade != null) {
			return trade;
		}else {
			throw new Exception();
		}		
	}

}
