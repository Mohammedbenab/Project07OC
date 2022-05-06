package com.nnk.springboot.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="rating")
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotBlank(message = "moodysRating is mandatory")
	private String moodysRating;
	@NotBlank(message = "sandPRating is mandatory")
	private String sandPRating;
	@NotBlank(message = "fitchRating is mandatory")
	private String fitchRating;
	@NotNull(message = "orderNumber is mandatory")
	private Integer orderNumber;
	public Rating() {
		super();
	}
	public Rating(@NotBlank(message = "moodysRating is mandatory") String moodysRating,
			@NotBlank(message = "sandPRating is mandatory") String sandPRating,
			@NotBlank(message = "fitchRating is mandatory") String fitchRating,
			@NotNull(message = "orderNumber is mandatory") Integer orderNumber) {
		super();
		this.moodysRating = moodysRating;
		this.sandPRating = sandPRating;
		this.fitchRating = fitchRating;
		this.orderNumber = orderNumber;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMoodysRating() {
		return moodysRating;
	}
	public void setMoodysRating(String moodysRating) {
		this.moodysRating = moodysRating;
	}
	public String getSandPRating() {
		return sandPRating;
	}
	public void setSandPRating(String sandPRating) {
		this.sandPRating = sandPRating;
	}
	public String getFitchRating() {
		return fitchRating;
	}
	public void setFitchRating(String fitchRating) {
		this.fitchRating = fitchRating;
	}
	public Integer getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	
}
