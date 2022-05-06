package com.nnk.springboot.service;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exception.BidNotFoundException;
import com.nnk.springboot.repository.BidListRepository;

@Service
public class BidListService {
	
	Logger logger = org.slf4j.LoggerFactory.getLogger(BidList.class);
	
	@Autowired
	private BidListRepository bidListRepository;
	
	/**
	 * 
	 * @param bidList
	 * @return
	 * @throws BidNotFoundException
	 */
	public BidList saveBidList(BidList bidList) throws BidNotFoundException {
//		BidList bidlist1 = bidListRepository.findByAccount(bidList.getAccount());
		if(bidList.getId() == null) {
			
//		if(bidlist1 == null) {
//			BidList bidListNew = new BidList();
//			bidListNew.setAccount(bidList.getAccount());
//			bidListNew.setBidQuantity(bidList.getBidQuantity());
//			bidListNew.setType(bidList.getType());
			bidListRepository.save(bidList);
			logger.info("BidList saved successfully");
			return bidList;
		}else {
			logger.error("BidList not saved");
			throw new BidNotFoundException("BidList can't be saved");
			}
		}
		
	
	/**
	 * 
	 * @param bidList
	 * @throws BidNotFoundException
	 */
	public void deleteBidList(BidList bidList) throws BidNotFoundException {
		BidList bidList1 = bidListRepository.findById(bidList.getId()).orElse(null);
		if(bidList1 != null) {
			logger.info("BidList deleted susscessfully");
			bidListRepository.delete(bidList1);
		}else {
			logger.error("BidList not deleted");
			throw new BidNotFoundException("BidList not deleted");
		}
	}
	
	/**
	 * 
	 * @param bidList
	 * @return
	 * @throws BidNotFoundException
	 */
	public BidList updateBidList(BidList bidList) throws BidNotFoundException {
		BidList bidList1 = bidListRepository.findById(bidList.getId()).orElse(null);
		if(bidList1 != null) {
			bidList1.setAccount(bidList.getAccount());
			bidList1.setBidQuantity(bidList.getBidQuantity());
			bidList1.setType(bidList.getType());
			bidListRepository.save(bidList1);
			logger.info("BidList updated and saved successfully");
			return bidList1;
		}else {
		logger.error("BidList not updated");
		throw new BidNotFoundException("BidList not updated and not saved");
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public BidList getbidListById(Integer id) {
		return bidListRepository.findById(id).orElse(null);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<BidList> getAllBidList(){
		return bidListRepository.findAll();
	}
	
	/**
	 * 
	 * @param account
	 * @return
	 */
	public BidList getBidListByAccount(String account) {
		return bidListRepository.findByAccount(account);
	}
}
