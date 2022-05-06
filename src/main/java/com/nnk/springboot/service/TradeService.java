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
	
	//add
	public Trade add(Trade trade) throws Exception {
		if(trade.getId() == null) {
			logger.info("Trade saved successfully");
			return tradeRepository.save(trade);
		}else {
			logger.error("Trade not saved");
			throw new Exception();
		}
	}
	
	//update
	public Trade update(Trade trade) throws Exception {
//		System.out.println("Trade id dans class service : "+trade.getId());
//		Trade tradeDb = tradeRepository.findById(trade.getId()).orElse(null);
		if(trade.getId() != null) {
			trade.setAccount(trade.getAccount());
			trade.setBuyQuantity(trade.getBuyQuantity());
			trade.setType(trade.getType());
			tradeRepository.save(trade);
			logger.info("Rule update successfully");
			return trade;
		}else {
			logger.error("Trade not updated");
			throw new Exception();
		}		
	}
	
	//delete
	public void delete(Trade trade) throws Exception {
		if(trade.getId() != null) {
			tradeRepository.delete(trade);
			logger.info("Trade has deleted successfully");
		}else {
			logger.error("Trade hasn't deleted");
			throw new Exception();
		}
		
	}
	
	//list
	public List<Trade> getList() {
		return tradeRepository.findAll();
		
		
	}
	
	//getById
	public Trade getById(Integer id) throws Exception {
		Trade trade = tradeRepository.findById(id).get();
		System.out.println("getbyid : "+trade.getId());
		return tradeRepository.findById(id).get();
		
	}

}
