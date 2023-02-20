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
public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7213092312818902191L;
	
	@Id
	private String id;
	private String name;
	private Double price;
	private Integer quantity;
	private Double totalPrice;
	private String transactionId;

}
