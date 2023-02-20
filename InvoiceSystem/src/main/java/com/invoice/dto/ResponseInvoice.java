package com.invoice.dto;

import java.io.Serializable;
import java.util.List;

import com.invoice.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseInvoice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8359006497103751272L;
	
	private String id;
	
	private List<Product> products;
	
	private String createdDate;
	private String createdTime;
	private String updatedDate;
	private String updatedTime;
	private String paymentStatus;
	private Double amount;
}
