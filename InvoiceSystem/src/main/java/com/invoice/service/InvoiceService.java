package com.invoice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.invoice.dto.ResponseInvoice;
import com.invoice.entity.Product;

@Service
public interface InvoiceService {

	public ResponseInvoice saveInvoice(List<Product> prods, Boolean payment) throws Exception;

	public ResponseInvoice updateInvoice(List<Product> prods, String id, Boolean payment) throws Exception;

	public ResponseInvoice getInvoice(String id) throws Exception;

	public List<ResponseInvoice> getAllInvoices() throws Exception;

	public boolean deleteInvoice(String id) throws Exception;

}
