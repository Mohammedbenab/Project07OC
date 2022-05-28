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
	 * This method allows adding a new trade if not exist in the data base
	 * @param trade is the object who to will be saved
	 * @return Trade added in the data base
	 * @throws Exception if the object Trade exist in the data base
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
	 * This method allows to update object if exist
	 * @param trade is the object who to will be updated
	 * @return Trade updated 
	 * @throws Exception if the object Trade exist in the data base
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
	 * This method allows to delete trade if exist in the data base
	 * @param trade who will be deleted
	 * @throws Exception if the object Trade exist in the data base
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
	 * This method allows to fetch all trades saved in the data base
	 * @return List of Trade
	 */
	public List<Trade> getList() {
		return tradeRepository.findAll();
		
		
	}
	
	/**
	 * this method allows to fetch trade by reference id if it exist in the data base
	 * @param id use to fetch trade saved in the data base
	 * @return Trade found by id if exist
	 * @throws Exception if the object Trade exist in the data base
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
