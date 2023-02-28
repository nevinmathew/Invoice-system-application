package com.invoice.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.invoice.dto.ResponseInvoice;
import com.invoice.entity.Product;
import com.invoice.service.InvoiceService;
import com.invoice.util.InvoicePDF;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/v1/invoice")
public class InvoiceController {
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private InvoicePDF pdf;
	
	@PostMapping("/save-invoice")
	public ResponseEntity<ResponseInvoice> saveInvoice(@RequestBody List<Product> prods, @RequestParam(required = false) Boolean payment, HttpServletResponse response) throws DocumentException, IOException  {
		try {
			
			ResponseInvoice invoice = invoiceService.saveInvoice(prods,payment);
			response.setContentType("application/pdf");
		    DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
		    String currentDateTime = dateFormat.format(new Date());
		    String headerkey = "Content-Disposition";
		    String headervalue = "attachment; filename=invoice" + currentDateTime + ".pdf";
		    response.setHeader(headerkey, headervalue);
			pdf.buildPdfDocument(invoice, response);
			return ResponseEntity.status(HttpStatus.CREATED).body(invoice);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}
	}
	
	@PatchMapping("/update-invoice/{id}")
	public ResponseEntity<?> updateInvoice(@RequestBody List<Product> prods, @PathVariable("id") String id, @RequestParam(required = false) Boolean payment) {
		try {
			return invoiceService.updateInvoice(prods,id,payment)!=null ? ResponseEntity.ok().body(invoiceService.updateInvoice(prods,id,payment)) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("The invoice does not exist.");
		} catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/get-invoice/{id}")
	public ResponseEntity<?> getInvoice(@PathVariable("id") String id) {
		try {
			return invoiceService.getInvoice(id)!=null ? ResponseEntity.status(HttpStatus.FOUND).body(invoiceService.getInvoice(id)):ResponseEntity.status(HttpStatus.NOT_FOUND).body("The invoice does not exist.");
		} catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/all-invoices")
	public ResponseEntity<List<ResponseInvoice>> getAllInvoices() {
		try {
			return invoiceService.getAllInvoices() != Collections.<ResponseInvoice>emptyList() ? ResponseEntity.ok().body(invoiceService.getAllInvoices()):ResponseEntity.status(HttpStatus.NO_CONTENT).body(invoiceService.getAllInvoices());
		} catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteInvoice(@PathVariable("id") String id) {
		try {
			return invoiceService.deleteInvoice(id) ? ResponseEntity.ok().body("The invoice "+id+" has been deleted.") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("The invoice "+id+" does not exist.");
		} catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

}
