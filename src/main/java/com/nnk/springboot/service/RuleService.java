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
	
	/**
	 * 
	 * @param rule
	 * @return new Rule 
	 * @throws Exception
	 */
	public Rule add(Rule rule) throws Exception {
		Rule rule1 = ruleRepository.findById(rule.getId()).orElse(null);
		if(rule1 == null) {
			logger.info("Rule saved successfully");
			return ruleRepository.save(rule);
		}else {
			logger.error("Rule not saved");
			throw new Exception();
		}
		
	}
	
	/**
	 * 
	 * @param rule
	 * @return Rule updated
	 * @throws Exception
	 */
	public Rule update(Rule rule) throws Exception {
		Rule ruleDB = ruleRepository.findById(rule.getId()).orElse(null);
		if(ruleDB != null) {
			ruleDB.setDescription(rule.getDescription());
			ruleDB.setJson(rule.getJson());
			ruleDB.setName(rule.getName());
			ruleDB.setSqlPart(rule.getSqlPart());
			ruleDB.setSqlStr(rule.getSqlStr());
			ruleDB.setTemplate(rule.getTemplate());
			logger.info("Rule update successfully");
			return ruleRepository.save(ruleDB);
		}else {
			logger.error("Rule not updated");
			throw new Exception();
		}
		
	}
	
	/**
	 * 
	 * @param rule
	 * @throws Exception
	 */
	public void delete(Rule rule) throws Exception {
		if(rule.getId() != null) {
			ruleRepository.delete(rule);
			logger.info("Rule has deleted successfully");
		}else {
			logger.error("Rule hasn't deleted");
			throw new Exception();
		}
		
	}
	
	/**
	 * 
	 * @return list of rules
	 */
	public List<Rule> getList() {
		return ruleRepository.findAll();
		
		
	}
	
	/**
	 * 
	 * @param id
	 * @return rule
	 * @throws Exception
	 */
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
