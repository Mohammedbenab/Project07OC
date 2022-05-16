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
import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.repository.RuleRepository;
import com.nnk.springboot.service.RuleService;

@SpringBootTest
public class RuleServiceUnitTest {
	
	@MockBean
	RuleRepository ruleRepository;
	
	@Autowired
	RuleService ruleService;
	
	Rule rule;
	
	@BeforeEach
	void setUp() {
		rule = new Rule("name", "description", "json", "template", "sqlStr", "sqlPart");
	}
	
	@Test
	void save() throws Exception {
		Mockito.when(ruleRepository.save(ArgumentMatchers.any(Rule.class))).thenReturn(rule);
		Rule ruleCreated = ruleService.add(rule);
		assertEquals(rule.getName(), ruleCreated.getName());
		verify(ruleRepository, Mockito.times(1)).save(rule);
	}
	
	@Test
	void update() throws Exception {
		rule.setId(1);
		Mockito.when(ruleRepository.findById(1)).thenReturn(Optional.of(rule));
		rule.setDescription("modification");;
		ruleService.update(rule);
		verify(ruleRepository, Mockito.times(1)).save(rule);
		verify(ruleRepository, Mockito.times(1)).findById(1);
	}
	
	@Test
	void delete() throws Exception {
		rule.setId(1);
		Mockito.when(ruleRepository.findById(1)).thenReturn(Optional.of(rule));
		ruleService.delete(rule);
		verify(ruleRepository, Mockito.times(1)).delete(rule);
	}
	
	@Test
	void getById() throws Exception {
		Mockito.when(ruleRepository.findById(1)).thenReturn(java.util.Optional.of(rule));
		ruleService.getById(1);
        verify(ruleRepository, Mockito.times(1)).findById(1);
        Assertions.assertEquals(rule, ruleService.getById(1));
		
	}
	
	@Test
	void getAll() {
		rule.setId(1);
    	List<Rule> list = new ArrayList<>();
    	list.add(rule);
    	Mockito.when(ruleRepository.findAll()).thenReturn(list);
        List<Rule> expected =  ruleService.getList();
        assertEquals(list,expected);
        verify(ruleRepository, Mockito.times(1)).findAll();
		
	}

}
