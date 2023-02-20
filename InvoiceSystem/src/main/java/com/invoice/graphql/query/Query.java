//package com.invoice.graphql.query;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.coxautodev.graphql.tools.GraphQLQueryResolver;
//import com.invoice.entity.Invoice;
//import com.invoice.repository.InvoiceRepository;
//
//@Component
//public class Query implements GraphQLQueryResolver {
//	
//	@Autowired
//	private InvoiceRepository repository;
//	
//	public Invoice getInvoice(String id) {
//		return repository.findById(id).get();
//	}
//	
//	public List<Invoice> getAllInvoices() {
//		return repository.findAll(); 
//	}
//}
