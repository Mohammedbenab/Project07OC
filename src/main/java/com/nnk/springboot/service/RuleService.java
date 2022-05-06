package com.nnk.springboot.service;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.repository.RuleRepository;

@Service
public class RuleService {
	
	Logger logger = org.slf4j.LoggerFactory.getLogger(RuleService.class);

	
	@Autowired
	private RuleRepository ruleRepository;
	
	//add
	public Rule add(Rule rule) throws Exception {
		if(rule.getId() == null) {
			logger.info("Rule saved successfully");
			return ruleRepository.save(rule);
		}else {
			logger.error("Rule not saved");
			throw new Exception();
		}
		
	}
	
	//update
	public Rule update(Rule rule) throws Exception {
		if(rule.getId() != null) {
			rule.setDescription(rule.getDescription());
			rule.setJson(rule.getJson());
			rule.setName(rule.getName());
			rule.setSqlPart(rule.getSqlPart());
			rule.setSqlStr(rule.getSqlStr());
			rule.setTemplate(rule.getTemplate());
			logger.info("Rule update successfully");
			return ruleRepository.save(rule);
		}else {
			logger.error("Rule not updated");
			throw new Exception();
		}
		
	}
	
	//delete
	public void delete(Rule rule) throws Exception {
		if(rule.getId() != null) {
			ruleRepository.delete(rule);
			logger.info("Rule has deleted successfully");
		}else {
			logger.error("Rule hasn't deleted");
			throw new Exception();
		}
		
	}
	
	//list
	public List<Rule> getList() {
		return ruleRepository.findAll();
		
		
	}
	
	//getById
	public Rule getById(Integer id) throws Exception {
		Rule rule = ruleRepository.findById(id).orElse(null);
		if(rule != null) {
			return rule;
		}else {
			logger.error("Rule not existe");
			throw new Exception();
		}		
	}


}
