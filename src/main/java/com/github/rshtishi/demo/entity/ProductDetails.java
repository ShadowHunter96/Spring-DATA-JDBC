package com.github.rshtishi.demo.entity;

import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("product_details")
public class ProductDetails {
	
	private String createdBy;
	private LocalDateTime createdOn;
	
	
	public ProductDetails(String createdBy, LocalDateTime createdOn) {
		super();
		this.createdBy = createdBy;
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
}
