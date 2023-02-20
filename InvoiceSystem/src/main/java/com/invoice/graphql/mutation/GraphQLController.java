package com.invoice.graphql.mutation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.invoice.dto.ResponseInvoice;
import com.invoice.entity.Product;
import com.invoice.service.InvoiceService;

@Controller
public class GraphQLController {
	
//	@Autowired
//	private ProductRepository repository;
//	
	@Autowired
	private InvoiceService service;
	
	@MutationMapping
	public ResponseInvoice saveInvoice(@Argument("prods") List<Product> prods, @Argument("payment") Boolean payment) throws Exception {
//		Product invoice = new Product();
//		invoice.setId(UUID.randomUUID().toString());
//		invoice.setName(inv.getName());
//		invoice.setAmount(inv.getAmount());
//		return repository.save(invoice);
		return service.saveInvoice(prods, payment);
	}
	
	@MutationMapping
	public ResponseInvoice updateInvoice(@Argument("prods") List<Product> prods, @Argument("id") String id, @Argument("payment") Boolean payment) throws Exception {
//		Product invoice = repository.findById(inv.getId()).get();
//		
//		if(invoice==null)
//			return null;
//			
//		if(inv.getName()!=null)
//			invoice.setName(inv.getName());
//			
//		if(inv.getAmount()!=null)
//			invoice.setAmount(inv.getAmount());
//			
//		return repository.save(invoice);
		return service.updateInvoice(prods, id, payment);
		
	}
	
	@MutationMapping
	public String deleteInvoice(@Argument("id") String id) throws Exception {
		
//		if(!repository.existsById(id))
//			return "The invoice "+id+" does not exist.";
//		
//		 repository.deleteById(id);
//		 return "The invoice "+id+" has been deleted.";
		return service.deleteInvoice(id) ? "The invoice "+id+" has been deleted." : "The invoice "+id+" does not exist.";
		
	}
	
	@QueryMapping
	public ResponseInvoice getInvoice(@Argument("id") String id) throws Exception {
//		return repository.findById(id).get();
		return service.getInvoice(id);
	}
	
	@QueryMapping
	public List<ResponseInvoice> getAllInvoices() throws Exception {
//		return repository.findAll(); 
		return service.getAllInvoices();
	}
}
