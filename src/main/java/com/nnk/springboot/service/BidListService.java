package com.nnk.springboot.service;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exception.BidNotFoundException;
import com.nnk.springboot.exception.CurvePointNotFoundException;
import com.nnk.springboot.repository.BidListRepository;

@Service
public class BidListService {
	
	Logger logger = org.slf4j.LoggerFactory.getLogger(BidList.class);
	
	@Autowired
	private BidListRepository bidListRepository;
	
	/**
	  * This method allows adding a new BidList if not exist in the data base
	 * @param BidList is the object who to will be saved
	 * @return BidList added in the data base
	 * @throws BidNotFoundException is called when BidList not found
	 */
	public BidList saveBidList(BidList bidList) throws BidNotFoundException {
		BidList bidlist1 = bidListRepository.findByAccount(bidList.getAccount());
		if(bidlist1 == null) {
			bidListRepository.save(bidList);
			logger.info("BidList saved successfully");
			return bidList;
		}else {
			logger.error("BidList not saved");
			throw new BidNotFoundException("BidList can't be saved");
			}
		}
		
	
	/**
	 * This method allows to delete BidList if exist in the data base
	 * @param BidList who will be deleted
	 * @throws BidNotFoundException is called when curvePoint not found
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
	 * This method allows to update object if exist
	 * @param BidList is the object who to will be updated
	 * @return BidList updated 
	 * @throws BidNotFoundException is called when curvePoint not found
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
	 *This method allows to fetch BidList by reference id if it exist in the data base
	 * @param id use to fetch BidList saved in the data base
	 * @return BidList found by id if exist
	 */
	public BidList getbidListById(Integer id) {
		return bidListRepository.findById(id).orElse(null);
	}
	
	/**
	  * This method allows to fetch all BidList saved in the data base
	 * @return List of BidList
	 */
	public List<BidList> getAllBidList(){
		return bidListRepository.findAll();
	}
	
	/**
	  * This method allows to fetch BidList saved by account
	  * @param account allows to found the object
	 * @return BidList found
	 */
	public BidList getBidListByAccount(String account) {
		return bidListRepository.findByAccount(account);
	}
}
