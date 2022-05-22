package com.nnk.springboot.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="bidList")
public class BidList {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
	@NotBlank(message = "Account is mandatory")
    private String account;
	@NotBlank(message = "Type is mandatory")
    private String type;
	@NotNull(message = "Numbers has to be present")
    private Double bidQuantity;
	
	public BidList() {
		super();
	}
 
	public BidList(@NotBlank(message = "Account is mandatory")String account,
			@NotBlank(message = "Type is mandatory") String type,
			@NotNull(message = "Numbers has to be present") Double bidQuantity) {
		super();
		this.account = account;
		this.type = type;
		this.bidQuantity = bidQuantity;
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

	public Double getBidQuantity() {
		return bidQuantity;
	}

	public void setBidQuantity(Double bidQuantity) {
		this.bidQuantity = bidQuantity;
	}
	
	
	
}


