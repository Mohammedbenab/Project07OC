package com.nnk.springboot.unitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exception.BidNotFoundException;
import com.nnk.springboot.repository.BidListRepository;
import com.nnk.springboot.service.BidListService;

@SpringBootTest
public class BidListServiceUnitTest {
	
	@MockBean
    BidListRepository bidListRepository;

    @Autowired
    BidListService bidListService;
    
    BidList bidList;

    @BeforeEach
    void setUp() {
    	bidList = new BidList("986532215487","type1",7.0);
    }

    @Test
    void findAllTest() {
    	bidList.setId(1);
    	List<BidList> list = new ArrayList<>();
    	list.add(bidList);
    	Mockito.when(bidListRepository.findAll()).thenReturn(list);
        List<BidList> expected =  bidListService.getAllBidList();
        assertEquals(list,expected);
        verify(bidListRepository, Mockito.times(1)).findAll();
    }
//
    @Test
    void findByIdTest() {
        Mockito.when(bidListRepository.findById(1)).thenReturn(java.util.Optional.of(bidList));
        bidListService.getbidListById(1);
        verify(bidListRepository, Mockito.times(1)).findById(1);
        Assertions.assertEquals(bidList, bidListService.getbidListById(1));
    }
//
//    @Test
//    void saveWhenBidExistExceptionTest() throws BidNotFoundException {
//    	Mockito.when(bidListRepository.findById(1));
//
//    }
//
    @Test
    void saveTest() throws BidNotFoundException {
    	Mockito.when(bidListRepository.save(ArgumentMatchers.any(BidList.class))).thenReturn(bidList);
        bidListService.saveBidList(bidList);
        verify(bidListRepository, Mockito.times(1)).save(bidList);
    }
    
    @Test
    void update() throws BidNotFoundException {
    	bidList.setId(1);
    	Mockito.when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));
    	bidList.setAccount("0000000");
    	bidListService.updateBidList(bidList);
    	verify(bidListRepository, Mockito.times(1)).save(bidList);
    	verify(bidListRepository, Mockito.times(1)).findById(1);
    }
    

    @Test
    void deleteTest() throws BidNotFoundException {
    	bidList.setId(1);
        Mockito.when(bidListRepository.findById(bidList.getId())).thenReturn(Optional.of(bidList));
        bidListService.deleteBidList(bidList);
        verify(bidListRepository, Mockito.times(1)).delete(bidList);
    }

}
