package com.invoice.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.invoice.dto.ResponseInvoice;
import com.invoice.entity.Product;
import com.invoice.entity.Transaction;
 import com.invoice.entity.enumeration.PaymentStatus;
import com.invoice.repository.ProductRepository;
import com.invoice.repository.TransactionRepository;
import com.invoice.service.InvoiceService;

import jakarta.transaction.Transactional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private TransactionRepository transactionRepo;

	private static final int tax = 15 / 100;

	@Override
	public ResponseInvoice saveInvoice(List<Product> prods, Boolean payment) throws Exception {

		try {
			ArrayList<Product> list = new ArrayList<>();

			Transaction transaction = new Transaction();
			transaction.setId(UUID.randomUUID().toString());

			Double amount = 0.0;

			for (Product prod : prods) {
				Product product = new Product();
				product.setId(UUID.randomUUID().toString());
				product.setName(prod.getName());
				product.setPrice(prod.getPrice() + (prod.getPrice() * tax));
				product.setQuantity(prod.getQuantity());
				product.setTotalPrice(product.getPrice() * product.getQuantity());
				product.setTransactionId(transaction.getId());
				amount += product.getTotalPrice();
				list.add(product);
				productRepo.save(product);
			}

			transaction.setCreatedDate(LocalDate.now().toString());
			transaction.setCreatedTime(LocalTime.now().toString());

			if (payment != null)
				transaction
						.setPaymentStatus(payment ? PaymentStatus.PAID.toString() : PaymentStatus.PENDING.toString());
			else
				transaction.setPaymentStatus(PaymentStatus.PAID.toString());

			transaction.setAmount(amount);
			transactionRepo.save(transaction);

			ResponseInvoice invoice = new ResponseInvoice();
			invoice.setId(transaction.getId());
			invoice.setProducts(list);
			invoice.setAmount(transaction.getAmount());
			invoice.setCreatedDate(transaction.getCreatedDate());
			invoice.setCreatedTime(transaction.getCreatedTime());
			invoice.setPaymentStatus(transaction.getPaymentStatus());
			invoice.setUpdatedDate(transaction.getUpdatedDate());
			invoice.setUpdatedTime(transaction.getUpdatedTime());
			return invoice;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Exception while saving");
		}
	}

	@Override
	@CachePut(value = "ResponseInvoice", key = "#id")
	public ResponseInvoice updateInvoice(List<Product> prods, String id, Boolean payment) throws Exception {

		try {
			// Only the edited product(s) will be sent in the param list of products

			Optional<Transaction> transaction = transactionRepo.findById(id);
			if (transaction.isEmpty())
				return null;

			ArrayList<Product> list = new ArrayList<>();

			Double amount = transaction.get().getAmount();

			List<Product> productList = productRepo.findByTransactionId(id);
			for (Product prod : prods) {

				Product product = productList.get(productList.indexOf(prod));
				if (prod.getName() != null)
					product.setName(prod.getName());
				if (prod.getPrice() != null)
					product.setPrice(prod.getPrice() + (prod.getPrice() * tax)); // send only if the price is altered
				if (prod.getQuantity() != null)
					product.setQuantity(prod.getQuantity());
				if (prod.getPrice() != null && prod.getQuantity() != null) {
					amount -= product.getTotalPrice(); // subtracted the price of this product from the total bill
														// amount
					product.setTotalPrice(product.getPrice() * product.getQuantity());
					amount += product.getTotalPrice(); // added the new price of this product from the total bill amount
				}
				list.add(product);
				productRepo.save(product);
			}

			transaction.get().setUpdatedDate(LocalDate.now().toString());
			transaction.get().setUpdatedTime(LocalTime.now().toString());
			if (payment != null)
				transaction.get().setPaymentStatus(payment ? PaymentStatus.PAID.toString() : PaymentStatus.PENDING.toString());
			transaction.get().setAmount(amount);
			transactionRepo.save(transaction.get());

			ResponseInvoice invoice = new ResponseInvoice();
			invoice.setId(id);
			invoice.setProducts(list);
			invoice.setAmount(transaction.get().getAmount());
			invoice.setCreatedDate(transaction.get().getCreatedDate());
			invoice.setCreatedTime(transaction.get().getCreatedTime());
			invoice.setPaymentStatus(transaction.get().getPaymentStatus());
			invoice.setUpdatedDate(transaction.get().getUpdatedDate());
			invoice.setUpdatedTime(transaction.get().getUpdatedTime());
			return invoice;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Exception while updating");
		}
	}

	@Override
	@Cacheable(value = "ResponseInvoice", key = "#id", unless = "#result.amount <= 10.0")
	public ResponseInvoice getInvoice(String id) throws Exception {

		try {
			Optional<Transaction> transaction = transactionRepo.findById(id);

			if (transaction.isEmpty())
				return null;

			List<Product> productList = productRepo.findByTransactionId(id);

			ResponseInvoice invoice = new ResponseInvoice();
			invoice.setId(id);
			invoice.setProducts(productList);
			invoice.setAmount(transaction.get().getAmount());
			invoice.setCreatedDate(transaction.get().getCreatedDate());
			invoice.setCreatedTime(transaction.get().getCreatedTime());
			invoice.setPaymentStatus(transaction.get().getPaymentStatus());
			invoice.setUpdatedDate(transaction.get().getUpdatedDate());
			invoice.setUpdatedTime(transaction.get().getUpdatedTime());
			return invoice;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Exception while fetching");
		}
	}

	@Override
//	@Cacheable(value = "Invoice")
	public List<ResponseInvoice> getAllInvoices() throws Exception {
		try {
			List<Transaction> transactions = transactionRepo.findAll();

			if (transactions.isEmpty())
				return Collections.<ResponseInvoice>emptyList();

			List<ResponseInvoice> invoices = new ArrayList<>();
			for (Transaction transaction : transactions) {
				List<Product> productList = productRepo.findByTransactionId(transaction.getId());
				ResponseInvoice invoice = new ResponseInvoice();
				invoice.setId(transaction.getId());
				invoice.setProducts(productList);
				invoice.setAmount(transaction.getAmount());
				invoice.setCreatedDate(transaction.getCreatedDate());
				invoice.setCreatedTime(transaction.getCreatedTime());
				invoice.setPaymentStatus(transaction.getPaymentStatus());
				invoice.setUpdatedDate(transaction.getUpdatedDate());
				invoice.setUpdatedTime(transaction.getUpdatedTime());
				invoices.add(invoice);
			}
			return invoices;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Exception while fetching all invoices");
		}
	}

	@Override
	@Transactional
	@Caching(evict = { @CacheEvict("ResponseInvoice"), @CacheEvict(value = "ResponseInvoice", key = "#Id", condition = "false") })
	public boolean deleteInvoice(String id) throws Exception {
		try {
			if (transactionRepo.existsById(id)) {
				productRepo.deleteByTransactionId(id);
				transactionRepo.deleteById(id);
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Exception while deleting");
		}
	}

}
