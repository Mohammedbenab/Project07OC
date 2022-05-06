package com.nnk.springboot.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="rule")
public class Rule {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotBlank(message = "name is mandatory")
	private String name;
	@NotBlank(message = "description is mandatory")
	private String description;
	@NotBlank(message = "json is mandatory")
	private String json;
	@NotBlank(message = "template is mandatory")
	private String template;
	@NotBlank(message = "sqlStr is mandatory")
	private String sqlStr;
	@NotBlank(message = "sqlPart is mandatory")
	private String sqlPart;
	
	public Rule() {
		super();
	}

	public Rule(@NotBlank(message = "name is mandatory") String name,
			@NotBlank(message = "description is mandatory") String description,
			@NotBlank(message = "json is mandatory") String json,
			@NotBlank(message = "template is mandatory") String template,
			@NotBlank(message = "sqlStr is mandatory") String sqlStr,
			@NotBlank(message = "sqlPart is mandatory") String sqlPart) {
		super();
		this.name = name;
		this.description = description;
		this.json = json;
		this.template = template;
		this.sqlStr = sqlStr;
		this.sqlPart = sqlPart;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getSqlStr() {
		return sqlStr;
	}

	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}

	public String getSqlPart() {
		return sqlPart;
	}

	public void setSqlPart(String sqlPart) {
		this.sqlPart = sqlPart;
	}
	
	
}
