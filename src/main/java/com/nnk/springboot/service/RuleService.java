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
	 * This method allows adding a new rule if not exist in the data base
	 * @param rule is the object who to will be saved
	 * @return rule added in the data base
	 * @throws Exception if the object rule exist in the data base
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
	 * This method allows to update object rule if exist
	 * @param rule is the object who to will be updated
	 * @return rule updated 
	 * @throws Exception if the object rule exist in the data base
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
	 * This method allows to delete rule if exist in the data base
	 * @param rule who will be deleted
	 * @throws Exception if the object rule exist in the data base
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
	 * This method allows to fetch all rules saved in the data base
	 * @return list of rules
	 */
	public List<Rule> getList() {
		return ruleRepository.findAll();
		
		
	}
	
	/**
	 * this method allows to fetch rule by reference id if it exist in the data base
	 * @param id use to fetch rule saved in the data base
	 * @return rule found by id if exist
	 * @throws Exception if the object rule exist in the data base
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
