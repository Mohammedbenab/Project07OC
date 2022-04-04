package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exception.BidNotFoundException;
import com.nnk.springboot.repository.BidListRepository;
import com.nnk.springboot.service.BidListService;

@SpringBootTest
public class BidListTest {

	@Autowired
	private BidListService bidListService;
	
	@Autowired
	private BidListRepository bidListRepository;
	
	private BidList bidList;
	
	@AfterEach
	void deleteAll() {
		bidListRepository.deleteAll();
	}
	
	@BeforeEach
	void setUp() {
		bidList = new BidList("03548765254", "type1", 5.0);
//		bidListService.saveBidList(bidList);
	}
	
	@Test
	void saveBidListWhenNotExist() throws BidNotFoundException {
		bidListService.saveBidList(bidList);
		assertEquals(1, bidListRepository.findAll().size());
		assertEquals("03548765254", bidListRepository.findAll().get(0).getAccount());
	}
	
	@Test
	void saveWhenBidExist() throws BidNotFoundException {
		bidListService.saveBidList(bidList);
		assertThrows(BidNotFoundException.class, ()->{
			bidListService.saveBidList(bidList);
		});
		assertEquals(1, bidListRepository.findAll().size());
	}
	
	@Test
	void deleteBidWhenExist() throws BidNotFoundException {
		bidListService.saveBidList(bidList);
		BidList bidDB = bidListRepository.findAll().get(0);
		int before = bidListRepository.findAll().size();
		bidListService.deleteBidList(bidDB);
		assertEquals(1, before);
		assertNotNull(bidDB.getId());
		assertEquals(0, bidListRepository.findAll().size());
		assertNotNull(bidDB);
	}
	
	@Test
	void deleteBidWhenNotExist() throws BidNotFoundException {
		BidList bidNotExist = new BidList("2548712364", "type1", 5.0);
		bidNotExist.setId(19);
		assertThrows(BidNotFoundException.class, ()->{
			bidListService.deleteBidList(bidNotExist);
		});
	}
	
	@Test 
	void updateBidWhenExist() throws BidNotFoundException{
		bidListService.saveBidList(bidList);
		BidList bidDB = bidListRepository.findAll().get(0);
		String accompteBefore = bidDB.getAccount();
		bidDB.setAccount("2154876325");
		bidListService.updateBidList(bidDB);
		String accompteAfter = bidDB.getAccount();
		assertNotNull(bidDB);
		assertEquals(1, bidListRepository.findAll().size());
		assertEquals("03548765254", accompteBefore);
		assertEquals("2154876325", accompteAfter);
	}
	
	@Test
	void updateWhenBidNotExist() {
		BidList bidNotExist = new BidList("2548712364", "type1", 5.0);
		bidNotExist.setId(19);
		assertThrows(BidNotFoundException.class, ()->{
			bidListService.updateBidList(bidNotExist);
		});
	}
	
	@Test
	void getBidById() throws BidNotFoundException {
		bidListService.saveBidList(bidList);
		BidList bidDB = bidListRepository.findAll().get(0);
		BidList bidById = bidListService.getbidListById(bidDB.getId());
		assertEquals(1, bidListRepository.findAll().size());
		assertNotNull(bidById);
	}
	
	@Test
	void getBidListWhenListIsNotEmpty() throws BidNotFoundException {
		bidListService.saveBidList(bidList);
		bidListService.saveBidList(new BidList("54215487", "type8", 8.0));
		List<BidList> list = bidListService.getAllBidList();
		assertEquals(2, bidListRepository.findAll().size());
	}
	
	@Test
	void getBidWhenListIsEmpty() {
		
	}
	
	@Test
	void getBidByAccount() throws BidNotFoundException {
		bidListService.saveBidList(bidList);
		BidList bidDB = bidListRepository.findAll().get(0);
		BidList byAccount = bidListService.getBidListByAccount(bidDB.getAccount());
		assertEquals(1, bidListRepository.findAll().size());
		assertEquals("03548765254", byAccount.getAccount());
	}
	
}
