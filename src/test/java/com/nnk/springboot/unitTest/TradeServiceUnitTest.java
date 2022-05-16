package com.nnk.springboot.unitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repository.TradeRepository;
import com.nnk.springboot.service.TradeService;

@SpringBootTest
public class TradeServiceUnitTest {
	@MockBean
	TradeRepository tradeRepository;
	
	@Autowired
	TradeService tradeService;
	
	Trade trade;
	
	@BeforeEach
	void setUp() {
		trade = new Trade("account", "type", 8.0);
	}
	
	@Test
	void save() throws Exception {
		Mockito.when(tradeRepository.save(ArgumentMatchers.any(Trade.class))).thenReturn(trade);
		Trade tradeCreated = tradeService.add(trade);
		assertEquals(trade.getAccount(), tradeCreated.getAccount());
		verify(tradeRepository, Mockito.times(1)).save(trade);
	}
	
	
	@Test
	void delete() throws Exception {
		trade.setId(1);
		Mockito.when(tradeRepository.save(ArgumentMatchers.any(Trade.class))).thenReturn(trade);
		tradeService.delete(trade);
		verify(tradeRepository, Mockito.times(1)).delete(trade);
	}
	
	@Test
	void getById() throws Exception {
		Mockito.when(tradeRepository.save(ArgumentMatchers.any(Trade.class))).thenReturn(trade);
		tradeService.getById(1);
        verify(tradeRepository, Mockito.times(1)).findById(1);
	}
	
	@Test
	void getAll() {
		trade.setId(1);
    	List<Trade> list = new ArrayList<>();
    	list.add(trade);
    	Mockito.when(tradeRepository.findAll()).thenReturn(list);
        List<Trade> expected =  tradeService.getList();
        assertEquals(list,expected);
        verify(tradeRepository, Mockito.times(1)).findAll();
	}

}
