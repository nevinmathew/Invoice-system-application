package com.invoice.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5687975414965689786L;
	
	@Id
	private String id;
	private String createdDate;
	private String createdTime;
	private String updatedDate;
	private String updatedTime;
	private String paymentStatus;
	private Double amount;
}
