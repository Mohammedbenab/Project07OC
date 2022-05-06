package com.nnk.springboot.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="trade")
public class Trade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotBlank(message = "account is mandatory")
	private String account;
	@NotBlank(message = "type is mandatory")
	private String type;
	@NotNull(message = "buyQuantity is mandatory")
	private Double buyQuantity;
	
	public Trade() {
		super();
	}

	public Trade(@NotBlank(message = "account is mandatory") String account,
			@NotBlank(message = "type is mandatory") String type,
			@NotNull(message = "buyQuantity is mandatory") Double buyQuantity) {
		super();
		this.account = account;
		this.type = type;
		this.buyQuantity = buyQuantity;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getBuyQuantity() {
		return buyQuantity;
	}

	public void setBuyQuantity(Double buyQuantity) {
		this.buyQuantity = buyQuantity;
	}

}
