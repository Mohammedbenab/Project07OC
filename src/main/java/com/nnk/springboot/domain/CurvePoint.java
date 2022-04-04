package com.nnk.springboot.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="curvePoint")
public class CurvePoint {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotBlank(message = "CurvePointId is mandatory")
	private String curve;
	@NotNull(message = "Term is mandatory")
	private Double term;
	@NotNull(message = "Value is mandatory")
	private Double value;
	
	public CurvePoint() {
		super();
	}

	public CurvePoint(@NotBlank(message = "CurvePointId is mandatory") String curve,
			@NotNull(message = "Term is mandatory") Double term,
			@NotNull(message = "Value is mandatory") Double value) {
		super();
		this.curve = curve;
		this.term = term;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCurve() {
		return curve;
	}

	public void setCurve(String curve) {
		this.curve = curve;
	}

	public Double getTerm() {
		return term;
	}

	public void setTerm(Double term) {
		this.term = term;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
	
	
}
